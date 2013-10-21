package xrank.ms.scoring.statistics.normalization;

import java.util.ArrayList;

import xrank.ms.match.SpSpMatchList;


public class NoNormalizator implements Normalizator {

	protected SpSpMatchList matchList;

	public void normalizeList() {
//
//		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();
//
//		matchIt = this.matchList.getMatchList().iterator();
//
//		while (matchIt.hasNext()) {
//			SpSpMatch match = matchIt.next();
//			
//			int count = 0;
//			for (ScoringValue val : match.getScoringValue()){
//				
//				if(count==0){
//					val.setZScore(
//							val.getScore());
//				}
//				count++;
//			}
//
//		}

	}

	public void setAdditionalScores(ArrayList scores) {
		// TODO Auto-generated method stub

	}

	public SpSpMatchList getMatchList() {
		return matchList;
	}

	public void setMatchList(SpSpMatchList matchList) {
		this.matchList = matchList;
	}

}
