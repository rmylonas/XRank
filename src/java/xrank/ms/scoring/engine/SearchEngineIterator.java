package xrank.ms.scoring.engine;

import java.util.List;

import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

import xrank.ms.match.MatchScore;
import xrank.ms.scoring.model.RC.parameters.RCParams;

public interface SearchEngineIterator {

	boolean hasNext();
	
	MatchScore next();
	
	void setupIterator(JPLFragmentationSpectrum expSp, List<JPLFragmentationSpectrum> refSpList, RCParams rCParams);
	
	public void setRCParams(RCParams rCParams);
	
	
}
