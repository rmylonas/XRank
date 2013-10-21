package xrank.ms.spectra;

import java.io.Serializable;


public class Reference implements Comparable<Reference>, Serializable {

	protected String refValue;
	protected String type;
	protected long spectrumInfoId;
	protected long referenceId;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Reference o) {

		if (this.getRefValue() != o.getRefValue()) {
			return 1;
		}

		return 0;
	}

	/**
	 * @return the refValue
	 */
	public String getRefValue() {
		return refValue;
	}

	/**
	 * @param refValue
	 *            the refValue to set
	 */
	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public long getSpectrumInfoId() {
		return spectrumInfoId;
	}

	public void setSpectrumInfoId(long spectrumInfoId) {
		this.spectrumInfoId = spectrumInfoId;
	}

	public long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(long referenceId) {
		this.referenceId = referenceId;
	}

}
