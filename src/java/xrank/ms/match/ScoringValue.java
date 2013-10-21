/* 
 *	%W% %E% Roman Mylonas
 *  
 */

package xrank.ms.match;

/**
 * The result-values from one scoring. Beside the raw score there is a optional
 * p-value and a z-score
 * 
 * @author roman
 * 
 */

public class ScoringValue implements Comparable<ScoringValue> {

	protected double score = -10000.0;
	protected double pValue = -10000.0;
	protected double zScore = -10000.0;

	/**
	 * First trys to compare the p-Values, next the score. If one of the two
	 * spectra doesn't contain a score, the other is considered to be greater.
	 * If both don't contain a score they're equal
	 * 
	 * @param aScoringValue
	 *            The object to compare has to be another ScoringValue
	 * @return Returns -1, 0, 1 if the intensity of a peak is smaller, equal,
	 *         greater than the specified one
	 */
	public int compareTo(ScoringValue aScoringValue) {
		if (this.zScore != -10000.0 && aScoringValue.zScore != -10000.0) {

			return compareZscore(aScoringValue);

		} else

		if (this.score != -10000.0 && aScoringValue.score != -10000.0) {

			return compareScore(aScoringValue);

		} else {

			if (this.score != -10000.0) {
				return 1;
			}
			if (aScoringValue.score != -10000.0) {
				return -1;
			}
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = new String("");

		if (this.score != -10000.0) {
			s += "Score: " + this.score + "\t";
		}

		if (this.pValue != -10000.0) {
			s += "p-Value: " + this.pValue + "\t";
		}

		if (this.zScore != -10000.0) {
			s += "z-Score: " + this.zScore;
		}

		return s;
	}

	/**
	 * compares the scores
	 * 
	 * @param aScoringValue
	 * @return int Returns -1, 0, 1 if this score is smaller, equal, greater
	 *         than to one it gets compared to
	 */
	protected int compareScore(ScoringValue aScoringValue) {

		if (this.score < aScoringValue.getScore()) {
			return 1;
		}
		if (this.score > aScoringValue.getScore()) {
			return -1;
		}

		return 1;
	}

	protected int compareZscore(ScoringValue aScoringValue) {

		if (this.zScore < aScoringValue.getZScore()) {
			return 1;
		}
		if (this.zScore > aScoringValue.getZScore()) {
			return -1;
		}

		return 1;
	}

	/**
	 * compares the p-Values
	 * 
	 * @param aScoringValue
	 * @return int Returns -1, 0, 1 if this p-Value is smaller, equal, greater
	 *         than to one it gets compared to
	 */
	protected int comparePValue(ScoringValue aScoringValue) {
		if (this.pValue > aScoringValue.getPValue()) {
			return 1;
		}
		if (this.pValue < aScoringValue.getPValue()) {
			return -1;
		}

		// TODO
		return 1;
	}

	/**
	 * @return the pValue
	 */
	public Double getPValue() {
		return pValue;
	}

	/**
	 * @param value
	 *            the pValue to set
	 */
	public void setPValue(Double value) {
		pValue = value;
	}

	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * @return the zScore
	 */
	public Double getZScore() {
		return zScore;
	}

	/**
	 * @param score
	 *            the zScore to set
	 */
	public void setZScore(Double score) {
		zScore = score;
	}

}
