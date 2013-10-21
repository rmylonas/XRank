/**
 * 
 */
package xrank.ms.scoring.statistics.friendlyScores;

import java.util.Iterator;

import xrank.ms.match.ScoringValue;
import xrank.ms.match.SpSpMatch;
import xrank.ms.match.SpSpMatchList;


/**
 * @author Roman Mylonas
 * 
 */
public class FriendlyScoreComputationImpl implements FriendlyScoreComputation {

	protected SpSpMatchList matchList;
	private final static double alpha = 0.15;
	private final static double m = 19.0;

	
	public void computeScores() {
		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();

		matchIt = this.matchList.getMatchList().iterator();

		while (matchIt.hasNext()) {
			SpSpMatch match = matchIt.next();
			
			int count =0;
			for (ScoringValue val : match.getScoringValue()) {
				
				if(count==0){
				double rawScore = val.getZScore();
				val.setZScore(this.computeFittedFunction(rawScore));
				count++;
				}
			}
		}

	}

	public Double computeFittedFunction(Double score) {
		double friendlyScore = 0.5 * (1 + Math.signum(score - m)
				* (1 - Math.exp(-alpha * Math.signum(score - m) * (score - m))));

		return friendlyScore;
	}

	
	public SpSpMatchList getMatchList() {
		return matchList;
	}


	public void setMatchList(SpSpMatchList spML) {
		this.matchList = spML;

	}

}
