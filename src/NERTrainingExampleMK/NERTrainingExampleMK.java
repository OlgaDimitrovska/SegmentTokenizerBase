package NERTrainingExampleMK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
import opennlp.uima.namefind.NameFinder;

public class NERTrainingExampleMK {

	public static void main(String[] args) throws IOException {
		
		
		
		//se vcituvaat training data vo ObjectStream
		InputStreamFactory in=null;
		try {
			in= new MarkableFileInputStreamFactory(new File("D:\\JavaNlpMk\\mk-ner.txt"));
		}
		catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		ObjectStream simpleStream=null;
				
		try {
			simpleStream=new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//Training parameters
		TrainingParameters params= new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 70);
		params.put(TrainingParameters.CUTOFF_PARAM, 1);
		
		
		//Train the model
		TokenNameFinderModel nameFinderModel=null;
		
		try {
			nameFinderModel=NameFinderME.train("mk", null, simpleStream, params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//Zacuvaj go modelot vo file
		File output= new File("D:\\JavaNlpMk\\ner-custom-model.bin");
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nameFinderModel.serialize(outputStream);
		
	}

}
