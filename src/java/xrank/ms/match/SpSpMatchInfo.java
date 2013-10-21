package xrank.ms.match;

/**
 * the interface for the match information. The idea is to provide information
 * for visualization of the scoring (e.g. the number of matches or which of the
 * peaks of two spectra where used for the scoring).
 * 
 * @author roman
 * 
 */

public interface SpSpMatchInfo {

	public String toString();

	public int getNbOfPeaksInCommon();

}
