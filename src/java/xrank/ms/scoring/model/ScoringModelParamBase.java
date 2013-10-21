/**
 *
 */
package xrank.ms.scoring.model;

/**
 * The abstract class which contains the common parameters used by the
 * {@link ScoringModel}.
 * 
 * @author Roman Mylonas
 * 
 */
public abstract class ScoringModelParamBase {

	protected Integer nrOfPeaks = null;
	private long id;

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

}
