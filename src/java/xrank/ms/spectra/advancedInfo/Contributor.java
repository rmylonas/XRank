package xrank.ms.spectra.advancedInfo;

import java.io.Serializable;

public class Contributor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6130162646548208879L;
	
	
	protected int contributorId;
	protected String contributorName;

	public Contributor() {
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public int getContributorId() {
		return contributorId;
	}

	public void setContributorId(int contributorId) {
		this.contributorId = contributorId;
	}

}
