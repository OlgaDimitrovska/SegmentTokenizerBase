package SentenceDetectorTrainingMK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class SentenceDetectorMK {

	public static void main(String[] args) {
		
		try {
			
			new SentenceDetectorMK().trainSentDetectModel();
		}
		
		catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	
	public void trainSentDetectModel() throws IOException{
		
		
		
		//директориум за да се зачува моделот што ќе биде генериран
		File destDir= new File("D:\\JavaNlpMk");
		
		//тренинг податоци
		InputStreamFactory in=new MarkableFileInputStreamFactory(new File("C:\\Users\\olgad\\Downloads\\mkd_wikipedia_2016_30K.tar\\mkd_wikipedia_2016_30K-sentences.txt"));
		
		//параметри користени од алгоритам за мапинско учење, Maximum Entropy, за да се тренираат weights
		TrainingParameters mlParams =new TrainingParameters();
		mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(15));
		mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));
		
		
		//treniraj go modelot
		@SuppressWarnings("deprecation")
		SentenceModel sentdetectModel = SentenceDetectorME.train("mk", new SentenceSampleStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8)), true, null, mlParams);
		
		
		//zacuvaj go modelot vo file, mk-sent-custom, vo destinaciskiot dir.
		File outFile=new File(destDir, "mk-sent-custom.bin");
		FileOutputStream outFileStream= new FileOutputStream(outFile);
		sentdetectModel.serialize(outFileStream);
		
		
		//vcitaj go modelot
		SentenceDetectorME sentDetector= new SentenceDetectorME(sentdetectModel);
		
		
		//detektiraj recenici vo testion string
		String testString=("Шеќерот е сладок. Но тоа не значи дека не е добар.");
		System.out.println("\nTestString: "+testString);
		String[] sents=sentDetector.sentDetect(testString);
		System.out.println("---------------------Detektirani recenici od Sentence Detector------------------");
		
		for(int i=0;i<sents.length;i++) {
			System.out.println("Sentence" +(i+1)+ " : "+sents[i]);
		}
		
		
		
		
	}
	
}
