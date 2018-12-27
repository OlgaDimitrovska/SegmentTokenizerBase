package DocumentCategorizer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import eus.ixa.ixa.pipe.nerc.train.AbstractTrainer;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.model.AbstractModel;
import opennlp.tools.ml.naivebayes.NaiveBayesModel;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.doccat.DoccatCrossValidator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerEvaluator;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderCrossValidator;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.eval.FMeasure;
import opennlp.uima.tokenize.Tokenizer;

public class OpenNLPCategorizer {
	 public static void main(String[] args) throws IOException {
		 
		 InputStreamFactory in=null;
			try {
				in= new MarkableFileInputStreamFactory(new File("D:\\JavaNlpMk\\mk doccategorizer\\2.doccategorizer.txt"));
			}
			catch (FileNotFoundException e2) {
				System.out.println("Kreiranje nov input stream");
				e2.printStackTrace();
			}
			
			ObjectStream lineStream=null;
			ObjectStream sampleStream=null;
					
			try {
				//simpleStream=new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
				
				lineStream = new PlainTextByLineStream(in, "UTF-8");
	            sampleStream = new DocumentSampleStream(lineStream);
				
			}
			catch (IOException e1) {
				System.out.println("Kreiranje Document Sample Stream");
				e1.printStackTrace();
			}
	            // read the training data
	            //InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("D:\\JavaNlpMk\\mk doccategorizer\\1.doccategorizer.txt"));
	             
	            // define the training parameters
	            TrainingParameters params = new TrainingParameters();
	            params.put(TrainingParameters.ITERATIONS_PARAM, 1000+"");
	            params.put(TrainingParameters.CUTOFF_PARAM, 1+"");
	            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
	 
	            // create a model from traning data
	            DoccatModel model=null;
				try {
					model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
				} catch (IOException e) {
					System.out.println("Treniranje model");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("\nModel is successfully trained.");
	 
	            // save the model to local
	            BufferedOutputStream modelOut=null;
				try {
					modelOut = new BufferedOutputStream(new FileOutputStream("D:\\JavaNlpMk\\mk doccategorizer\\classifier-maxent.bin"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Kreiranje output stream");
					e.printStackTrace();
				}
	            try {
					model.serialize(modelOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Serijalizacija na modelot");
					e.printStackTrace();
				}
	            System.out.println("\nTreniraniot model lokalno se zacuva vo: "+"model"+File.separator+"en-slucai-classifier-maxent.bin");
	 
	            // test the model file by subjecting it to prediction
	            DocumentCategorizer doccat = new DocumentCategorizerME(model);
	            String[] docWords = "Oсновниот суд во Штип, преку стечајниот судија Маја Џорлева, со Решение Ст. бр. 7/18 од 4.7.2018 година, огласува дека Предлогот за отворање на стечајна постапка над должникот Друштво за производство, трговија, транспорт и услуги МИТАШ ТРАНС ДООЕЛ Пробиштип, со седиште на ул. ЈНА бр. 146, Пробиштип, со ЕМБС 6797610, со ЕДБ 4022012502689, е повлечен. Поведената претходна постапка за утврдување на причините за отворање стечајна постапка над должникот, се запира. Решението на Основен суд Штип, I Ст. бр. 7/18 од 1.6.2018 година, со кое се определени мерки за обезбедување на имотот на должникот, се укинува. Привремениот стечаен управник Пепи Страшо Панев ТП Штип, определен со Решение на Основен суд Штип, I Ст. бр. 7/18 од 1.6.2018 година, се разрешува. Се утврдуваат трошоците на постапка со износ од 25.000 денари и нив ги поднесува предлагачот. Против ова Решение не е дозволена жалба согласно со член 52, ст. 2 од Законот за стечај. Од Основен суд – Штип.".replaceAll("[^A-Za-z]", " ").split(" ");
	            //String[] docWords =  "Основниот суд во Прилеп  преку стечајниот судија Доста Лукароска во присуство на привремениот стечаен управник Љубица Зајкоска, со Решение  Ст. бр. 1/2018  од  30.5.2018  година, отвори стечајна постапка спрема  должникот    Друштво за производство, промет и услуги Гога Свеќароски ГОВАМА    увоз-извоз    ДООЕЛ  Прилеп, ул. „Цане Илиоски“ бр. 17 Прилеп, запишано во Решението на Централен регистар на РМ, со ЕМБС  5414571  од 27.12.1999 година, со жиро-сметка бр. 500000000479886 депонент на Стопанска банка АД Битола и ЕДБ  4021000144640. Претежна дејност 47.71 – Трговија на мало со облека во специјализирани продавници. Стечајната постапка спрема должникот  не се спроведува  и  се заклучува. Централниот регистар по правосилноста на ова Решение да го  брише  должникот од својата евиденција. Од Основен суд – Прилеп.".replaceAll("[^A-Za-z]", " ").split(" ");
	            
	            
	            double[] aProbs = doccat.categorize(docWords);
	 
	            // print the probabilities of the categories
	            System.out.println("\n---------------------------------\nKategorija : Verojatnost\n---------------------------------");
	            for(int i=0;i<doccat.getNumberOfCategories();i++){
	                System.out.println(doccat.getCategory(i)+" : "+aProbs[i]);
	            }
	            System.out.println("---------------------------------");
	 
	            System.out.println("\n"+doccat.getBestCategory(aProbs)+" : e predvidenata kategorija za dadenata recenica");
	        
	            //List<EvaluationMonitor<DocumentSample>> listeners = new LinkedList<EvaluationMonitor<DocumentSample>>();
	            
	            DoccatCrossValidator evaluator = new DoccatCrossValidator("en", params, new DoccatFactory());
	    		evaluator.evaluate(sampleStream, 10);

	    		System.out.println(evaluator.getDocumentAccuracy());
	            
	    }
	
	
	
}
