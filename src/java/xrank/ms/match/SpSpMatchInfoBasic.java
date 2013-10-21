/**
 * 
 */
package xrank.ms.match;

/**
 * Cointains only the number of matches between two spectra. Used by simple
 * scoring models, such as dotProduct
 * 
 * @author roman
 * 
 */

public class SpSpMatchInfoBasic implements SpSpMatchInfo {

	int nbOfPeaksInCommon = -1;

	/**
	 * @return the nbOfPeaksInCommon
	 */
	public int getNbOfPeaksInCommon() {
		return nbOfPeaksInCommon;
	}

	/**
	 * @param nbOfPeaksInCommon
	 *            the nbOfPeaksInCommon to set
	 */
	public void setNbOfPeaksInCommon(int nbOfPeaksInCommon) {
		this.nbOfPeaksInCommon = nbOfPeaksInCommon;
	}

	public String toString() {
		String s = "";

		s = "nrOfPeaksInCommon:\t" + this.nbOfPeaksInCommon;

		return s;
	}

}
