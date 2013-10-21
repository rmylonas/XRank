package xrank.ms.scoring.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

/**
 * the abstract class for the spectra to spectra scoring algorithms.
 * 
 * @author Roman Mylonas
 * 
 */

public abstract class ScoringModelBase implements ScoringModel {

	// protected RankCorrelationReader reader;
	protected Log currentLogger = null;

	public ScoringModelBase() {
		this.currentLogger = LogFactory.getLog(this.getClass());
	}

	protected Double calcPrecursorDifference(JPLFragmentationSpectrum spA,
			JPLFragmentationSpectrum spB) {
		Double diff = null;
		Double mozA = null;
		Double mozB = null;

		mozA = spA.getPrecursor().getMz();
		mozB = spB.getPrecursor().getMz();

		this.currentLogger.debug("spA prec-moz: " + mozA);
		this.currentLogger.debug("spB prec-moz: " + mozB);

		if (mozA != null && mozB != null) {
			diff = Math.abs(mozA - mozB);
			this.currentLogger.debug("diff: " + diff);
		} else {
			diff = 999999.0;
		}

		return diff;
	}

}
