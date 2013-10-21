package xrank.ms.spectra.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.junit.Test;

import xrank.tests.BasicSpringTest;

public class JPLRunLcmsmsReaderMZXMLTest extends BasicSpringTest{

	@Resource
	JPLRunReaderBasic jPLRunReaderBasic;	
	
	@Test
	public void testCocaineFile() {

		String filename = this.getClass().getResource("/spectra/Cocaine_RB4_01_540.mzXML").getPath();

		LocalJPLRunLcmsmsReaderMZXML reader = new LocalJPLRunLcmsmsReaderMZXML(jPLRunReaderBasic);
		JPLRunLcmsms run = null;
		
		try {
			run = reader.buildRun(filename);
		} catch (JPLReaderException e) {
			e.printStackTrace();
			fail();
		}
	
		assertEquals(1098102, (int)run.getChromatogramXIC().getIntensityAt(1));
		assertEquals(795, run.getFragmentationSpectraList().size());
		
		int nb_peaks =  run.getFragmentationSpectraList().get(4).getNbPeak();
		
		assertEquals(23, nb_peaks);
		
		assertEquals(304, (int) run.getFragmentationSpectraList().get(4).getMzAt(nb_peaks-1));
		
		assertEquals(930, (int) run.getFragmentationSpectraList().get(4).getIntensityAt(nb_peaks-1));
		

	}
	
}
