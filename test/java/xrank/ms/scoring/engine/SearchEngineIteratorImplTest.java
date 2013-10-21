package xrank.ms.scoring.engine;

import javax.annotation.Resource;

import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReaderMGFLocal;
import org.junit.Test;
import static org.junit.Assert.*;

import xrank.ms.match.MatchScore;
import xrank.ms.scoring.model.RC.parameters.RCParams;
import xrank.ms.scoring.model.RC.parameters.RankCorrelationReader;
import xrank.tests.BasicSpringTest;

public class SearchEngineIteratorImplTest extends BasicSpringTest {

	@Resource
	SearchEngineIterator searchEngineIterator;
	
	@Resource
	RankCorrelationReader rankCorrelationReader;

	@Test
	public void testGetIdResultsOneMatch() {
		// Spectra Loading
		JPLRunLcmsms expRun = null;
		JPLRunLcmsms refRun = null;

		String filenameExp = "test/data/spectra/test-4.mgf";
		String filenameRefList = "test/data/spectra/test-3.mgf";
		String rcConfigFile = "test/data/scoring/rank_correlation-config.xml";
		
		// Load the general parameters
		RCParams rCParams = rankCorrelationReader.readXmlConfig(rcConfigFile);
		
		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();

		try {
			expRun = reader.buildRun(filenameExp);
			refRun = reader.buildRun(filenameRefList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		searchEngineIterator.setupIterator(expRun.getFragmentationSpectraList().get(0), refRun.getFragmentationSpectraList(), rCParams);
		
		MatchScore matchScore = null;
		if(searchEngineIterator.hasNext()){
			matchScore = searchEngineIterator.next();
		}
		
		assertEquals("AppliedBiosystems CE: 35", matchScore.getExpSp());
		assertEquals("Compound-1 CE: 35", matchScore.getRefSp());
		assertEquals(29.798528387500003, matchScore.getScore(), 0.001);
		assertEquals(0.9010288057823161, matchScore.getNormalizedScore(), 0.001);
		
	}
	
	@Test
	public void testGetIdResultsAllMatches() {
		// Spectra Loading
		JPLRunLcmsms expRun = null;
		JPLRunLcmsms refRun = null;

		String filenameExp = "test/data/spectra/test-4.mgf";
		String filenameRefList = "test/data/spectra/test-3.mgf";
		String rcConfigFile = "test/data/scoring/rank_correlation-config.xml";
		
		// Load the general parameters
		RCParams rCParams = rankCorrelationReader.readXmlConfig(rcConfigFile);
		
		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();

		try {
			expRun = reader.buildRun(filenameExp);
			refRun = reader.buildRun(filenameRefList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		searchEngineIterator.setupIterator(expRun.getFragmentationSpectraList().get(0), refRun.getFragmentationSpectraList(), rCParams);
		int nr = 0;
		double scoreSum = 0;
		
		while(searchEngineIterator.hasNext()){
			MatchScore matchScore = searchEngineIterator.next();
			nr++;
			scoreSum += matchScore.getScore();
		}
		
		assertEquals(3, nr);
		assertEquals(71.47519039950001, scoreSum, 0.001);	
	}
	
}
