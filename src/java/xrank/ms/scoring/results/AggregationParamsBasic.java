package xrank.ms.scoring.results;

/**
 * Contains the parameters used by {@link AggregateMatchesBasic}.
 * 
 * @author roman
 * 
 */

public class AggregationParamsBasic extends AggregationParams {

	Integer maxNrOfMatchesPerCompound = null;
	Integer id;

	Boolean sortByScore = null;

	/**
	 * @return the maxNrOfMatchesPerCompound
	 */
	public Integer getMaxNrOfMatchesPerCompound() {
		return maxNrOfMatchesPerCompound;
	}

	/**
	 * @param maxNrOfMatchesPerCompound
	 *            the maxNrOfMatchesPerCompound to set
	 */
	public void setMaxNrOfMatchesPerCompound(Integer maxNrOfMatchesPerCompound) {
		this.maxNrOfMatchesPerCompound = maxNrOfMatchesPerCompound;
	}

	/**
	 * @return the sortByScore
	 */
	public Boolean getSortByScore() {
		return sortByScore;
	}

	/**
	 * @param sortByScore
	 *            the sortByScore to set
	 */
	public void setSortByScore(Boolean sortByScore) {
		this.sortByScore = sortByScore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
