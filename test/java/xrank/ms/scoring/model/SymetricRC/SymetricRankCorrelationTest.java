package xrank.ms.scoring.model.SymetricRC;

import javax.annotation.Resource;

import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.filtering.JPLSpectrumFilter;
import org.expasy.jpl.experimental.ms.lcmsms.filtering.filter.JPLSpectrumOnePeakPerWindowFilterLocal;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReaderMGFLocal;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.utils.sort.SimpleTypeArray;
import org.junit.Test;
import static org.junit.Assert.*;
import xrank.ms.scoring.model.RC.parameters.RCParams;
import xrank.ms.scoring.model.RC.parameters.RankCorrelationReader;
import xrank.tests.BasicSpringTest;


public class SymetricRankCorrelationTest extends BasicSpringTest {

	@Resource
	SymetricRankCorrelation symetricRankCorrelation;
	
	@Resource
	RankCorrelationReader rankCorrelationReader;
	
	@Test
	public void testScoringCelinAsyn() throws Exception {

		// Load the spectra
		JPLFragmentationSpectrum spA = null;
		JPLFragmentationSpectrum spB = null;

		JPLRunLcmsms runA = null;
		JPLRunLcmsms runB = null;

		String filenameA = "test/data/spectra/celine_240054.mgf";
		String filenameB = "test/data/spectra/celine_223489.mgf";
		String rcConfigFile = "test/data/scoring/rank_correlation-config.xml";

		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();

		try {
			runA = reader.buildRun(filenameA);
			runB = reader.buildRun(filenameB);
		} catch (Exception e) {
			e.printStackTrace();
		}

		spA = runA.getFragmentationSpectraList().get(0);
		spB = runB.getFragmentationSpectraList().get(0);

		JPLSpectrumFilter filter = new JPLSpectrumOnePeakPerWindowFilterLocal();
		filter.setLevel(0.7);

		// Filter the spectra
		spA = filter.filterSpectrum(spA);
		spB = filter.filterSpectrum(spB);
		
		// Set the engine with the correct search parameter set
		RCParams rCParams = rankCorrelationReader.readXmlConfig(rcConfigFile);		
		symetricRankCorrelation.setParams(rCParams);
		
		assertEquals(15.336609693000002, symetricRankCorrelation.generateScore(spA,
				spB, SimpleTypeArray.sortIndexesDown(spA.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spB.getIntensities()))
				.getScore(), 0.001);
		assertEquals(15.336609693000002, symetricRankCorrelation.generateScore(spB,
				spA, SimpleTypeArray.sortIndexesDown(spB.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spA.getIntensities()))
				.getScore(), 0.001);

	}
	
	@Test
	public void testScoringB() throws Exception {

		// Load the spectra
		JPLFragmentationSpectrum spA = null;
		JPLFragmentationSpectrum spB = null;

		JPLRunLcmsms runA = null;
		JPLRunLcmsms runB = null;

		String filenameA = "test/data/spectra/moclobemide-1.mgf";
		String filenameB = "test/data/spectra/moclobemide-2.mgf";
		String rcConfigFile = "test/data/scoring/rank_correlation-config.xml";

		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
		
		try {
			runA = reader.buildRun(filenameA);
			runB = reader.buildRun(filenameB);
		} catch (Exception e) {
			e.printStackTrace();
		}

		spA = runA.getFragmentationSpectraList().get(0);
		spB = runB.getFragmentationSpectraList().get(0);

		JPLSpectrumFilter filter = new JPLSpectrumOnePeakPerWindowFilterLocal();
		filter.setLevel(0.7);

		// Filter the spectra
		spA = filter.filterSpectrum(spA);
		spB = filter.filterSpectrum(spB);

		// Set the engine with the correct search parameter set
		RCParams rCParams = rankCorrelationReader.readXmlConfig(rcConfigFile);
		symetricRankCorrelation.setParams(rCParams);
		
		assertEquals(20.577676933, symetricRankCorrelation.generateScore(spA,
				spB, SimpleTypeArray.sortIndexesDown(spA.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spB.getIntensities()))
				.getScore(), 0.001);
		assertEquals(20.577676933, symetricRankCorrelation.generateScore(spB,
				spA, SimpleTypeArray.sortIndexesDown(spB.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spA.getIntensities()))
				.getScore(), 0.001);

	}

	@Test
	public void testScoringC() {

		// Load the spectra
		JPLFragmentationSpectrum spA = null;
		JPLFragmentationSpectrum spB = null;

		JPLRunLcmsms runA = null;
		JPLRunLcmsms runB = null;

		String filenameA = "test/data/spectra/mgf_export-1.mgf";
		String filenameB = "test/data/spectra/mgf_export-2.mgf";
		String rcConfigFile = "test/data/scoring/rank_correlation-config.xml";

		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
		
		try {
			runA = reader.buildRun(filenameA);
			runB = reader.buildRun(filenameB);
		} catch (Exception e) {
			e.printStackTrace();
		}

		spA = runA.getFragmentationSpectraList().get(0);

		spB = runB.getFragmentationSpectraList().get(0);

		JPLSpectrumFilter filter = new JPLSpectrumOnePeakPerWindowFilterLocal();
		filter.setLevel(0.7);

		// Filter the spectra
		spA = filter.filterSpectrum(spA);
		spB = filter.filterSpectrum(spB);

		// Set the engine with the correct search parameter set
		RCParams rCParams = rankCorrelationReader.readXmlConfig(rcConfigFile);
		symetricRankCorrelation.setParams(rCParams);

		assertEquals(15.318400909000001, symetricRankCorrelation.generateScore(
				spA, spB,
				SimpleTypeArray.sortIndexesDown(spA.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spB.getIntensities()))
				.getScore(), 0.001);
		assertEquals(15.318400909000001, symetricRankCorrelation.generateScore(
				spB, spA,
				SimpleTypeArray.sortIndexesDown(spB.getIntensities()),
				SimpleTypeArray.sortIndexesDown(spA.getIntensities()))
				.getScore(), 0.001);

	}

	public SymetricRankCorrelation getSymetricRankCorrelation() {
		return symetricRankCorrelation;
	}

	public void setSymetricRankCorrelation(
			SymetricRankCorrelation symetricRankCorrelation) {
		this.symetricRankCorrelation = symetricRankCorrelation;
	}

}
