package xrank.ms.spectra.loader;


import org.expasy.jpl.experimental.exceptions.JPLImpossibleToBuildChromatogram;
import org.expasy.jpl.experimental.exceptions.JPLIncompatibleRTUnitException;
import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReader;
import org.expasy.jpl.utils.exceptions.JPLException;


public class JPLRunReaderBasic extends JPLRunLcmsmsReader{

	public JPLRunReaderBasic() {
		super();
	}
	
	public JPLRunReaderBasic(JPLRunReaderBasic jPLRunReaderBasic) {
		super();
	}

	@Override
	public void buildChromatogramTIC(JPLRunLcmsms run)
			throws JPLIncompatibleRTUnitException,
			JPLImpossibleToBuildChromatogram {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildChromatogramXIC(JPLRunLcmsms run)
			throws JPLIncompatibleRTUnitException,
			JPLImpossibleToBuildChromatogram {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPLRunLcmsms buildRunImpl(String src) throws JPLReaderException {
		JPLRunLcmsms run=buildRunImpl(src);
		try {
			buildChromatogramTIC(run);
			buildChromatogramXIC(run);
		} catch (JPLException e) {
			throw new JPLReaderException(e);
		}
		return run;
	}
	

}
