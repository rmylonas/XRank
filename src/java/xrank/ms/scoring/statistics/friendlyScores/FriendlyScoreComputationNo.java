package xrank.ms.scoring.statistics.friendlyScores;

import xrank.ms.match.SpSpMatchList;

public class FriendlyScoreComputationNo implements FriendlyScoreComputation {
	protected SpSpMatchList matchList;


	public void computeScores() {

		// don't do nothing

	}


	public SpSpMatchList getMatchList() {
		return matchList;
	}


	public void setMatchList(SpSpMatchList spML) {
		this.matchList = spML;

	}

	@Override
	public Double computeFittedFunction(Double score) {
		return score;
	}
}
