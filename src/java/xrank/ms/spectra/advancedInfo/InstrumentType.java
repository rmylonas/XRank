/**
 * 
 */
package xrank.ms.spectra.advancedInfo;

import java.io.Serializable;

/**
 * @author roman
 * 
 */
public class InstrumentType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916012467247075655L;
	
	
	protected int InstrumentTypeId;
	protected String InstrumentTypeName;

	public InstrumentType() {
	}

	/**
	 * @return the instrumentTypeId
	 */
	public int getInstrumentTypeId() {
		return InstrumentTypeId;
	}

	/**
	 * @param instrumentTypeId
	 *            the instrumentTypeId to set
	 */
	public void setInstrumentTypeId(int instrumentTypeId) {
		InstrumentTypeId = instrumentTypeId;
	}

	/**
	 * @return the instrumentTypeName
	 */
	public String getInstrumentTypeName() {
		return InstrumentTypeName;
	}

	/**
	 * @param instrumentTypeName
	 *            the instrumentTypeName to set
	 */
	public void setInstrumentTypeName(String instrumentTypeName) {
		InstrumentTypeName = instrumentTypeName;
	}

}
