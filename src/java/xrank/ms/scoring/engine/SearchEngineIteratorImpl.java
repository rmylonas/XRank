package xrank.ms.scoring.engine;

import java.util.List;

import javax.annotation.Resource;

import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.utils.sort.SimpleTypeArray;

import xrank.ms.match.MatchScore;
import xrank.ms.match.ScoringValue;
import xrank.ms.scoring.model.RC.parameters.RCParams;
import xrank.ms.scoring.model.SymetricRC.SymetricRankCorrelation;
import xrank.ms.scoring.statistics.friendlyScores.FriendlyScoreComputation;

public class SearchEngineIteratorImpl implements SearchEngineIterator {
	
	@Resource
	SymetricRankCorrelation symetricRankCorrelation;
	
	@Resource
	FriendlyScoreComputation friendlyScoreComputation;
	
	JPLFragmentationSpectrum expSp = null;
	List<JPLFragmentationSpectrum> refSpList = null;
	RCParams rCParams = null;
	
	Integer i = 0;

	public SearchEngineIteratorImpl() {
		super();
		
	}

	@Override
	public boolean hasNext() {
		if(this.expSp == null || this.refSpList == null) return false;
		
		if(this.i >= refSpList.size()){
			return false;
		}
		return true;
	}

	@Override
	public MatchScore next() {
		JPLFragmentationSpectrum refSp = this.refSpList.get(this.i);
		
		ScoringValue scoringValue = symetricRankCorrelation.generateScore(this.expSp, refSp, SimpleTypeArray.sortIndexesDown(this.expSp.getIntensities()),
				SimpleTypeArray.sortIndexesDown(refSp.getIntensities()));
		
		MatchScore matchScore = new MatchScore();
		matchScore.setExpSp(this.expSp.getTitle());
		matchScore.setRefSp(refSp.getTitle());
		matchScore.setScore(scoringValue.getScore());
		matchScore.setNormalizedScore(this.friendlyScoreComputation.computeFittedFunction(scoringValue.getScore()));
		
		this.i ++;
		return matchScore;
	}

	@Override
	public void setupIterator(JPLFragmentationSpectrum expSp,
			List<JPLFragmentationSpectrum> refSpList, RCParams rCParams) {
		
		this.expSp = expSp;
		this.refSpList = refSpList;
		symetricRankCorrelation.setParams(rCParams);
		this.i = 0;
	}
	
	public void setRCParams(RCParams rCParams){
		this.rCParams = rCParams;
		symetricRankCorrelation.setParams(rCParams);
	}


}
