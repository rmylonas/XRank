package xrank.ms.scoring.model;


import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

import xrank.ms.match.ScoringValue;


/**
 * The interface for spectra to spectra matching.
 * 
 * @author Roman Mylonas
 * 
 */

public interface ScoringModel {

	public ScoringValue generateScore(JPLFragmentationSpectrum spA,
			JPLFragmentationSpectrum spB, int[] indexesA, int[] indexesB);

	public Integer getNrOfPeaks();

	public Float getMozThershold();

}
