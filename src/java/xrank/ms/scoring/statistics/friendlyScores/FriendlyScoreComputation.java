/**
 * 
 */
package xrank.ms.scoring.statistics.friendlyScores;

/**
 * @author Roman Mylonas
 * 
 */

import xrank.ms.match.SpSpMatchList;

public interface FriendlyScoreComputation {

	public void setMatchList(SpSpMatchList spML);

	public void computeScores();

	public SpSpMatchList getMatchList();
	
	public Double computeFittedFunction(Double score);

}
