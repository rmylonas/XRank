/**
 * 
 */
package xrank.ms.scoring.statistics.normalization;

import xrank.ms.match.SpSpMatchList;

/**
 * @author roman
 * 
 */
public abstract class MatchListNormalizatorBase implements
		MatchListNormalizator {

	protected SpSpMatchList matchList = null;


	public void setMatchList(SpSpMatchList matchList) {
		this.matchList = matchList;
	}

	public SpSpMatchList getMatchList() {
		return matchList;
	}

}
