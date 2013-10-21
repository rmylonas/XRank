package xrank.ms.spectra;

import java.io.Serializable;
import java.util.SortedSet;

import org.expasy.jpl.experimental.ms.peak.JPLExpMSPeakLC;

import xrank.ms.spectra.advancedInfo.Contributor;
import xrank.ms.spectra.advancedInfo.Instrument;
import xrank.ms.spectra.advancedInfo.Method;

public class MsSpectrumInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6838351791468603036L;
	
	// Direct properties
	protected long spectrumInfoId;
	protected long spectrumId;
	protected String spectrumTitle; // Title of the spectrum
	protected String ionisation; // Type of ionisation source
	protected String collisionEnergy; // Collision energy
	protected double retentionTime; // Retention time expressed in seconds

	// Link to other classes
	protected Contributor contributor; // Contributor of the spectrum eg.
	// Veronique Viette
	protected Instrument instrument; // Description of the instrument
	// experiment
	protected SortedSet<Reference> ref; // Collection of references for the
	// spectrum
	protected JPLExpMSPeakLC precurcor;
	protected Method method; // Experimental design used
	
//	//Calculation purpose
//	MsSpectrumBitSet msSpectrumBitSet;

	public MsSpectrumInfo() {
		
	}

	/**
	 * @return the spectrumTitle
	 */
	public String getSpectrumTitle() {
		return spectrumTitle;
	}

	/**
	 * @param spectrumTitle
	 *            the spectrumTitle to set
	 */
	public void setSpectrumTitle(String spectrumTitle) {
		this.spectrumTitle = spectrumTitle;
	}

	/**
	 * @return the ionisation
	 */
	public String getIonisation() {
		return ionisation;
	}

	/**
	 * @param ionisation
	 *            the ionisation to set
	 */
	public void setIonisation(String ionisation) {
		this.ionisation = ionisation;
	}

	/**
	 * @return the collisionEnergy
	 */
	public String getCollisionEnergy() {
		return collisionEnergy;
	}

	/**
	 * @param collisionEnergy
	 *            the collisionEnergy to set
	 */
	public void setCollisionEnergy(String collisionEnergy) {
		this.collisionEnergy = collisionEnergy;
	}

	/**
	 * @return the retentionTime
	 */
	public double getRetentionTime() {
		return retentionTime;
	}

	/**
	 * @param retentionTime
	 *            the retentionTime to set
	 */
	public void setRetentionTime(double retentionTime) {
		this.retentionTime = retentionTime;
	}


	/**
	 * @return the ref
	 */
	public SortedSet<Reference> getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(SortedSet<Reference> ref) {
		this.ref = ref;
	}

	public Contributor getContributor() {
		return contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public long getSpectrumId() {
		return spectrumId;
	}

	public void setSpectrumId(long spectrumId) {
		this.spectrumId = spectrumId;
	}

	public long getSpectrumInfoId() {
		return spectrumInfoId;
	}

	public void setSpectrumInfoId(long spectrumInfoId) {
		this.spectrumInfoId = spectrumInfoId;
	}

	public JPLExpMSPeakLC getPrecurcor() {
		return precurcor;
	}

	public void setPrecurcor(JPLExpMSPeakLC precurcor) {
		this.precurcor = precurcor;
	}



//	public MsSpectrumBitSet getMsSpectrumBitSet() {
//		return msSpectrumBitSet;
//	}
//
//	public void setMsSpectrumBitSet(MsSpectrumBitSet msSpectrumBitSet) {
//		this.msSpectrumBitSet = msSpectrumBitSet;
//	}

}
