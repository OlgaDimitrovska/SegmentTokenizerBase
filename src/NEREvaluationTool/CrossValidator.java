package NEREvaluationTool;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eus.ixa.ixa.pipe.nerc.features.XMLFeatureDescriptor;
import eus.ixa.ixa.pipe.nerc.train.AbstractTrainer;
import eus.ixa.ixa.pipe.nerc.train.FixedTrainer;
import eus.ixa.ixa.pipe.nerc.train.Flags;
import opennlp.tools.cmdline.namefind.NameEvaluationErrorListener;
import opennlp.tools.cmdline.namefind.TokenNameFinderDetailedFMeasureListener;
import opennlp.tools.namefind.BilouCodec;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleTypeFilter;
import opennlp.tools.namefind.TokenNameFinderCrossValidator;
import opennlp.tools.namefind.TokenNameFinderEvaluationMonitor;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.SequenceCodec;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.eval.EvaluationMonitor;

public class CrossValidator {
	
	private String lang;
	private String trainData;
	private ObjectStream<NameSample> trainSamples;
	
	
	
	private int beamSize;
	private int folds;
	private SequenceCodec<String> sequenceCodec;
	
	private String corpusFormat;
	private TokenNameFinderFactory nameClassifierFactory;
	private List<EvaluationMonitor<NameSample>> listeners= new LinkedList<EvaluationMonitor<NameSample>>();
	
	
	private CrossValidator(final TrainingParameters params) throws IOException{
		
		this.lang=Flags.getLanguage(params);
		String clearFeatures=Flags.getClearTrainingFeatures(params);
		this.corpusFormat=Flags.getClearTrainingFeatures(params);
		this.trainData=params.getSettings().get("TrainSet");
		trainSamples=AbstractTrainer.getNameStream(trainData,clearFeatures, corpusFormat);
		this.beamSize=Flags.getBeamsize(params);
		this.sequenceCodec=TokenNameFinderFactory.instantiateSequenceCodec(getSequenceCodec(Flags.getSequenceCodec(params)));
		
		if(params.getSettings().get("Types")!=null) {
			
			String netypes=params.getSettings().get("Types");
			String[] neTypes=netypes.split(",");
			trainSamples=new NameSampleTypeFilter(neTypes, trainSamples);
			
		}
		
		createNameFactory(params);
		getEvalListeners(params);
		
	}
	
	private void createNameFactory(TrainingParameters params) throws IOException {
	    String featureDescription = XMLFeatureDescriptor
	        .createXMLFeatureDescriptor(params);
	    System.err.println(featureDescription);
	    byte[] featureGeneratorBytes = featureDescription.getBytes(Charset
	        .forName("UTF-8"));
	    Map<String, Object> resources = FixedTrainer.loadResources(params, featureGeneratorBytes);
	    this.nameClassifierFactory = TokenNameFinderFactory.create(
	        TokenNameFinderFactory.class.getName(), featureGeneratorBytes,
	        resources, sequenceCodec);
	  }
	
	private void getEvalListeners(TrainingParameters params) {
	    if (params.getSettings().get("EvaluationType").equalsIgnoreCase("error")) {
	      listeners.add(new NameEvaluationErrorListener());
	    }
	    if (params.getSettings().get("EvaluationType").equalsIgnoreCase("detailed")) {
	      detailedFListener = new TokenNameFinderDetailedFMeasureListener();
	      listeners.add(detailedFListener);
	    }
	  }
	
	public final void crossValidate(final TrainingParameters params) {
	    if (nameClassifierFactory == null) {
	      throw new IllegalStateException(
	          "Classes derived from AbstractNameFinderTrainer must create and fill the AdaptiveFeatureGenerator features!");
	    }
	    TokenNameFinderCrossValidator validator = null;
	    try {
	      validator = new TokenNameFinderCrossValidator(lang,
	          null, params, nameClassifierFactory,
	          listeners.toArray(new TokenNameFinderEvaluationMonitor[listeners.size()]));
	      validator.evaluate(trainSamples, folds);
	    } catch (IOException e) {
	      System.err.println("IO error while loading training set!");
	      e.printStackTrace();
	      System.exit(1);
	    } finally {
	      try {
	        trainSamples.close();
	      } catch (IOException e) {
	        System.err.println("IO error with the train samples!");
	      }
	    }
	    if (detailedFListener == null) {
	      System.out.println(validator.getFMeasure());
	    } else {
	      System.out.println(detailedFListener.toString());
	    }
	  }
	
	public final String getSequenceCodec(String seqCodecOption) {
	    String seqCodec = null;
	    if ("BIO".equals(seqCodecOption)) {
	      seqCodec = BioCodec.class.getName();
	    }
	    else if ("BILOU".equals(seqCodecOption)) {
	      seqCodec = BilouCodec.class.getName();
	    }
	    return seqCodec;
	  }
	  
	  public final int getBeamSize() {
	    return beamSize;
	  }
	
	
	
}
