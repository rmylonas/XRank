package xrank.ms.match;

public class MatchScore {

	private Double score;
	private Double normalizedScore;
	private String expSp;
	private String refSp;
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getNormalizedScore() {
		return normalizedScore;
	}
	public void setNormalizedScore(Double normalizedScore) {
		this.normalizedScore = normalizedScore;
	}
	public String getExpSp() {
		return expSp;
	}
	public void setExpSp(String expSp) {
		this.expSp = expSp;
	}
	public String getRefSp() {
		return refSp;
	}
	public void setRefSp(String refSp) {
		this.refSp = refSp;
	}
	
}
