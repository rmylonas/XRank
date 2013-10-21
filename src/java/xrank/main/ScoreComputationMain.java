package xrank.main;


import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReaderMGFLocal;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.utils.sort.SimpleTypeArray;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xrank.ms.match.ScoringValue;
import xrank.ms.scoring.model.RC.parameters.RCParams;
import xrank.ms.scoring.model.RC.parameters.RankCorrelationReader;
import xrank.ms.scoring.model.SymetricRC.SymetricRankCorrelation;
import xrank.ms.scoring.statistics.friendlyScores.FriendlyScoreComputation;
import xrank.ms.spectra.loader.JPLRunReaderBasic;
import xrank.ms.spectra.loader.LocalJPLRunLcmsmsReaderMZXML;

public class ScoreComputationMain {
	
	static JPLRunReaderBasic jPLRunReaderBasic = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Message if there were not the required 2 params given
		if(args.length != 3){
			System.err.println("usage: java -jar xrank.jar [/path/to/unknown mgf|mzXML] [/path/to/reference mgf|mzXML] [/path/to/rank_correlation_config.xml]");
		}else{
			String[] contextPaths = new String[] {"applicationContext.xml"};
			ApplicationContext ctx = new ClassPathXmlApplicationContext(contextPaths);
			SymetricRankCorrelation symetricRankCorrelation = (SymetricRankCorrelation) ctx.getBean("symetricRankCorrelation");
			FriendlyScoreComputation normalizedScoreComputation = (FriendlyScoreComputation) ctx.getBean("friendlyScoreComputation");
			RankCorrelationReader rankCorrelationReader = (RankCorrelationReader) ctx.getBean("rankCorrelationReader");
			
			// Load the rank-correlation config file
			//String xmlConfigPath = ScoreComputationMain.class.getResource("/rank_correlation-config.xml").getPath();
			String xmlConfigPath = args[2];
			RCParams rCParams = rankCorrelationReader.readXmlConfig(xmlConfigPath);
			symetricRankCorrelation.setParams(rCParams);
			
			// Load the runs to analyse
			List<JPLFragmentationSpectrum> expSpList = loadRun(args[0]);
			List<JPLFragmentationSpectrum> refSpList = loadRun(args[1]);
			
			// TODO: filter spectra
			
			for(JPLFragmentationSpectrum expSp: expSpList){
				for(JPLFragmentationSpectrum refSp: refSpList){
					ScoringValue score = symetricRankCorrelation.generateScore(expSp, refSp, SimpleTypeArray.sortIndexesDown(expSp.getIntensities()), SimpleTypeArray.sortIndexesDown(refSp.getIntensities()));
					Double normScore = normalizedScoreComputation.computeFittedFunction(score.getScore());
					System.out.println(expSp.getTitle() + "," + refSp.getTitle() + "," + normScore);
				}
			}
			
		}

	}
	
	protected static List<JPLFragmentationSpectrum> loadRun(String pathToFile){
		JPLRunLcmsms run = null;
		
		String extension = getExtension(pathToFile);
		File file = new File(pathToFile);
		
		if(!file.exists()){
			throw new RuntimeException("file [ " + pathToFile + "] does not exist");
		}
		
		if(extension.compareToIgnoreCase("mgf") == 0){
			JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
			
			try {
				run = reader.buildRun(pathToFile);
				return run.getFragmentationSpectraList();
			} catch (JPLReaderException e) {
				throw new RuntimeException(e);
			}
		}
		
		if(extension.compareToIgnoreCase("mzXML") == 0){
			LocalJPLRunLcmsmsReaderMZXML reader = new LocalJPLRunLcmsmsReaderMZXML();
			
			try {
				run = reader.buildRun(pathToFile);
				return run.getFragmentationSpectraList();
			} catch (JPLReaderException e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
	
	protected static String getExtension(String filename){
		Pattern p = Pattern.compile("\\.(\\w+)$");

		Matcher m = p.matcher(filename);

		// create one Peak
		if (m.find()) {
			String format = m.group(1);
			return format;
		} else {
			throw new RuntimeException("Could not parse file extension from [" + filename + "]");
		}
	}

}
