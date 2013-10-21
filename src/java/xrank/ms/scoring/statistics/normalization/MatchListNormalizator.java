/**
 * 
 */
package xrank.ms.scoring.statistics.normalization;

import xrank.ms.match.SpSpMatchList;

/**
 * @author roman
 * 
 */
public interface MatchListNormalizator {

	/**
	 * Set the match list you want to normalize.
	 * 
	 * @param matchList
	 */
	void setMatchList(SpSpMatchList matchList);

	/**
	 * Set the correct value (zscore, pvalues, ..) for all the elements of
	 * getMatchList.
	 */
	void normalizeList();

	/**
	 * Print data ready to plot an histogram.
	 * 
	 * @return
	 */
	String histogramString();

	/**
	 * returns a histogram of theoretical values (used for testing).
	 * 
	 * @return
	 */
	double[][] theoreticalHistogram();

	/**
	 * returns data ready to plot an histogram.
	 * 
	 * @return
	 */
	double[][] histogram();

	/**
	 * Print a histogram of theoretical values (used for testing).
	 * 
	 * @return
	 */
	String theoreticalHistogramString();

	/**
	 * Produces an array with n numbers which follow the distribution (used for
	 * testing)
	 * 
	 * @param n
	 * @return
	 */
	double[] generateRandom(final int n);

	/**
	 * Produces a random value which follows the distribution (used for testing)
	 * 
	 * @return
	 */
	double generateRandom();
}
