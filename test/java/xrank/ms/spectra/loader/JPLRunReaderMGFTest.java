package xrank.ms.spectra.loader;

import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReaderMGFLocal;
import org.junit.Test;
import static org.junit.Assert.*;
import xrank.tests.BasicSpringTest;


public class JPLRunReaderMGFTest extends BasicSpringTest{

	@Test
	public void testBrukerMGF() {
		
		String filename = this.getClass().getResource("/spectra/T.100110.0027_urine_926_1-A,5_01_364.d.mgf").getPath();
		
		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
		JPLRunLcmsms run = null;
		
		try {
			run = reader.buildRun(filename);
		} catch (JPLReaderException e) {
			e.printStackTrace();
		}
		
		assertEquals(915, run.getFragmentationSpectraList().size());
		
	}
	
	
	@Test
	public void testExportMGF() {

		String filename = this.getClass().getResource("/spectra/mgf_export-1.mgf").getPath();

		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
		JPLRunLcmsms run = null;
		
		try {
			run = reader.buildRun(filename);
		} catch (JPLReaderException e) {
			e.printStackTrace();
		}

		assertEquals(1, run.getFragmentationSpectraList().size());
		
	}
	
	
	@Test
	public void testVeroniqueMGF() {

		String filename = this.getClass().getResource("/spectra/veronique-1.mgf").getPath();

		JPLRunLcmsmsReaderMGFLocal reader = new JPLRunLcmsmsReaderMGFLocal();
		JPLRunLcmsms run = null;
		
		try {
			run = reader.buildRun(filename);
		} catch (JPLReaderException e) {
			e.printStackTrace();
		}

		assertEquals(58, run.getFragmentationSpectraList().size());
		
	}

}
