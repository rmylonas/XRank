/*
 *	%W% %E% Roman Mylonas
 *
 */

package xrank.ms.match;

import java.util.List;

import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

import xrank.ms.scoring.model.ScoringModel;


/**
 * Stores information about the matches between two spectra (ScoringValue,
 * MatchInfo and ScoringModel)
 * 
 * @author roman
 */

public class SpSpMatch implements Comparable<SpSpMatch> {

	protected JPLFragmentationSpectrum spectrumA = null;
	protected JPLFragmentationSpectrum spectrumB = null;
	protected List<ScoringValue> scoringValue = null;
	protected SpSpMatchInfo matchInfo = null;
	protected ScoringModel scoringModel = null;
	protected RankScore rank = null;
	protected RetentionTimeInfo retentionTimeInfo = null;
	protected double precursorDifference = -1;

	/**
	 * @return String of the two spectra, the scoreValue and the matchInfo
	 */
	public String toString() {
		String s = new String("");

		s += "Spectrum A: \n"
				+ (spectrumA != null ? spectrumA.toString() : "null") + "\n";
		s += "Spectrum B: \n"
				+ (spectrumB != null ? spectrumB.toString() : "null") + "\n";
		s += "ScoreValue: \n" + scoringValue.toString() + "\n";
		s += "MatchInfo: \n" + matchInfo.toString() + "\n";

		return s;
	}

	/**
	 * Compares the p-Values if available, otherwise the raw scores
	 * 
	 * @param aSpSpMatch
	 * @return Returns -1, 0, 1 if the p-Value (or Score) is smaller, equal,
	 *         greater than the specified one
	 */
	public int compareTo(SpSpMatch aSpSpMatch) {
		return (this.scoringValue.get(0).compareTo(aSpSpMatch.scoringValue.get(0)));
	}

	/**
	 * @return the scoreValue
	 */
	public List<ScoringValue> getScoringValue() {
		return scoringValue;
	}

	/**
	 * @param scoreValue
	 *            the scoreValue to set
	 */
	public void setScoringValue(List<ScoringValue> scoringValue) {
		this.scoringValue = scoringValue;
	}

	public JPLFragmentationSpectrum getSpectrumA() {
		return spectrumA;
	}

	public void setSpectrumA(JPLFragmentationSpectrum spectrumA) {
		this.spectrumA = spectrumA;
	}

	public JPLFragmentationSpectrum getSpectrumB() {
		return spectrumB;
	}

	public void setSpectrumB(JPLFragmentationSpectrum spectrumB) {
		this.spectrumB = spectrumB;
	}

	/**
	 * @return the matchInfo
	 */
	public SpSpMatchInfo getMatchInfo() {
		return matchInfo;
	}

	/**
	 * @param matchInfo
	 *            the matchInfo to set
	 */
	public void setMatchInfo(SpSpMatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	/**
	 * @return the scoringModel
	 */
	public ScoringModel getScoringModel() {
		return scoringModel;
	}

	/**
	 * @param scoringModel
	 *            the scoringModel to set
	 */
	public void setScoringModel(ScoringModel scoringModel) {
		this.scoringModel = scoringModel;
	}

	/**
	 * @return the rank
	 */
	public RankScore getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(RankScore rank) {
		this.rank = rank;
	}

	public RetentionTimeInfo getRetentionTimeInfo() {
		return retentionTimeInfo;
	}

	public void setRetentionTimeInfo(RetentionTimeInfo retentionTimeInfo) {
		this.retentionTimeInfo = retentionTimeInfo;
	}

	public double getPrecursorDifference() {
		return precursorDifference;
	}

	public void setPrecursorDifference(double precursorDifference) {
		this.precursorDifference = precursorDifference;
	}

}
