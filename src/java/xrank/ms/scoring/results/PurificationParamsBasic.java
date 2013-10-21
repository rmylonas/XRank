package xrank.ms.scoring.results;

/**
 * The parameters used by
 * 
 * @author roman
 * 
 */

public class PurificationParamsBasic extends PurificationParams {

	private Double pValueThreshold = null;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getpValueThreshold() {
		return pValueThreshold;
	}

	public void setpValueThreshold(Double valueThreshold) {
		pValueThreshold = valueThreshold;
	}

}
