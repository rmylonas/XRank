/**
 *
 */
package xrank.ms.scoring.model.RC.parameters;

import java.util.List;

import xrank.ms.scoring.model.ScoringModelParamBase;


/**
 * @author Roman Mylonas
 * 
 */
public class RCParams extends ScoringModelParamBase {

	protected Double matchTolerance = null;
	protected Integer nrOfPeaks = null;
	protected Integer nrOfRanks = null;

	protected List<List<Double>> probabilities = null;
	protected String probabilitiesString;

	private long id;

	/**
	 * @return the matchTolerance
	 */
	public Double getMatchTolerance() {
		return matchTolerance;
	}

	/**
	 * @param matchTolerance
	 *            the matchTolerance to set
	 */
	public void setMatchTolerance(Double matchTolerance) {
		this.matchTolerance = matchTolerance;
	}

	/**
	 * @return the nrOfRanks
	 */
	public Integer getNrOfRanks() {
		return nrOfRanks;
	}

	/**
	 * @param nrOfRanks
	 *            the nrOfRanks to set
	 */
	public void setNrOfRanks(Integer nrOfRanks) {
		this.nrOfRanks = nrOfRanks;
	}

	/**
	 * @return the nrOfPeaks
	 */
	public Integer getNrOfPeaks() {
		return nrOfPeaks;
	}

	/**
	 * @param nrOfPeaks
	 *            the nrOfPeaks to set
	 */
	public void setNrOfPeaks(Integer nrOfPeaks) {
		this.nrOfPeaks = nrOfPeaks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<List<Double>> getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(List<List<Double>> probabilities) {
		this.probabilities = probabilities;
	}

	public String getProbabilitiesString() {
		return probabilitiesString;
	}

	public void setProbabilitiesString(String probabilitiesString) {
		this.probabilitiesString = probabilitiesString;
	}

}
