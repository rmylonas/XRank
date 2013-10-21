package xrank.ms.spectra.advancedInfo;

import java.io.Serializable;

public class Instrument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6805679026484705309L;
	
	
	protected int instrumentId;
	protected String instrumentName;
	protected InstrumentType instrumentType;

	public Instrument() {
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	/**
	 * @return the instrumentType
	 */
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @param instrumentType
	 *            the instrumentType to set
	 */
	public void setInstrumentType(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
	}

}
