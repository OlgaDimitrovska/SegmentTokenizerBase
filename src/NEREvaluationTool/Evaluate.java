package NEREvaluationTool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import eus.ixa.ixa.pipe.nerc.train.Flags;
import opennlp.tools.cmdline.namefind.NameEvaluationErrorListener;
import opennlp.tools.cmdline.namefind.TokenNameFinderDetailedFMeasureListener;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleTypeFilter;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderEvaluationMonitor;
import opennlp.tools.namefind.TokenNameFinderEvaluator;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.eval.EvaluationMonitor;

public class Evaluate {
	
	
	private ObjectStream<NameSample> testSamples;
	
	private NameFinderME nameFinder;
	
	private static ConcurrentHashMap<String, TokenNameFinderModel> nercModels= new ConcurrentHashMap<String,TokenNameFinderModel>();
	
	public Evaluate(final Properties props) throws IOException{
		
		String lang= props.getProperty("language");
		String clearFeatures=props.getProperty("clearFeatures");
		String model=props.getProperty("model");
		String testSet=props.getProperty("testSet");
		String corpusFormat=props.getProperty("corpusFormat");
		String netypes=props.getProperty("types");
		
		testSamples=AbstractTrainer.getNameStream(testSet, clearFeatures, corpusFormat);
		if(netypes!=Flags.DEFAULT_NE_TYPES) {
			String[] neTypes=netypes.split(",");
			testSamples= new NameSampleTypeFilter(neTypes,testSamples);
		
		}
		
		nercModels.putIfAbsent(lang, new TokenNameFinderModel(new FileInputStream(model)));
		nameFinder= new NameFinderME(nercModels.get(lang));
	
	}
	
	
	public final void evaluate() throws IOException{
		
		
		TokenNameFinderEvaluator evaluator= new TokenNameFinderEvaluator(nameFinder);
		evaluator.evaluate(testSamples);
		System.out.println(evaluator.getFMeasure());
		
		
		
	}
	
	
	public final void detaiEvaluate() throws IOException{
		
		List<EvaluationMonitor<NameSample>> listeners= new LinkedList<EvaluationMonitor<NameSample>>();
		TokenNameFinderDetailedFMeasureListener detailedFListener= new TokenNameFinderDetailedFMeasureListener();
		listeners.add(detailedFListener);
		TokenNameFinderEvaluator evaluator= new TokenNameFinderEvaluator(nameFinder, listeners.toArray(new TokenNameFinderEvaluationMonitor[listeners.size()]));
		
		evaluator.evaluate(testSamples);
		System.out.println(detailedFListener.toString());
		
	}
	
	
	public final void evalError() throws IOException{
		
		List<EvaluationMonitor<NameSample>> listeners= new LinkedList<EvaluationMonitor<NameSample>>();
		listeners.add(new NameEvaluationErrorListener());
		TokenNameFinderEvaluator evaluator=new TokenNameFinderEvaluator(nameFinder, listeners.toArray(new TokenNameFinderEvaluationMonitor[listeners.size()]));
		evaluator.evaluate(testSamples);
		System.out.println(evaluator.getFMeasure());
		
	}
	
	
}
