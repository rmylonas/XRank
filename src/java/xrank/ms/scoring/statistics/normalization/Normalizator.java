package xrank.ms.scoring.statistics.normalization;

import xrank.ms.match.SpSpMatchList;

public interface Normalizator {

	public void setMatchList(SpSpMatchList spML);

	public void normalizeList();

	public SpSpMatchList getMatchList();

}
