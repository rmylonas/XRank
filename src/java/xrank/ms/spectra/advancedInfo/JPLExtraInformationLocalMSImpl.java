/**
 * 
 */
package xrank.ms.spectra.advancedInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import org.expasy.jpl.experimental.ms.lcmsms.JPLExtraInformation;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime;
import org.expasy.jpl.experimental.ms.peak.JPLExpMSPeakLC;

import xrank.ms.spectra.Reference;


/**
 * @author Roman Mylonas
 *
 */
public class JPLExtraInformationLocalMSImpl implements JPLExtraInformation, Serializable {


	private static final long serialVersionUID = 1L;
	
	/**
	 * Internal id, mainly used for Serialization and persistence (Hibernate) purposes
	 */
	protected long id;
	
	/**
	 * Ionisation source
	 */
	//TODO stored as a free string but sould be as an object
	protected String ionisation;
	
	/**
	 * Collision energy
	 */
	//TODO stored as a free string but sould be as an object
	protected String collisionEnergy;
	
	/**
	 * The Map<Method, JPLRetentionTime> allows to store on retion time per method
	 */
	protected Map<Method, JPLRetentionTime> specificRetentionTime;
	
	/**
	 * Contributor of the spectrum eg.Veronique Viette
	 */
	protected Contributor contributor;
	
	/**
	 * Description of the instrument
	 */
	protected Instrument instrument;
	
	
	/**
	 * Collection of references for the spectrum
	 */
	protected SortedSet<Reference> references;
	
	/**
	 * JPL JPLExpMSPeakLC object
	 */
	protected JPLExpMSPeakLC precurcor;
	
	
	/* (non-Javadoc)
	 * @see org.expasy.jpl.experimental.ms.lcmsms.JPLExtraInformation#toHashmap()
	 */
	public HashMap<String, String> toHashmap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void fromHashmap(HashMap<String, String> properties){
		
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ionisation
	 */
	public String getIonisation() {
		return ionisation;
	}

	/**
	 * @param ionisation the ionisation to set
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
	 * @param collisionEnergy the collisionEnergy to set
	 */
	public void setCollisionEnergy(String collisionEnergy) {
		this.collisionEnergy = collisionEnergy;
	}

	/**
	 * @return the specificRetentionTime
	 */
	public Map<Method, JPLRetentionTime> getSpecificRetentionTime() {
		return specificRetentionTime;
	}

	/**
	 * @param specificRetentionTime the specificRetentionTime to set
	 */
	public void setSpecificRetentionTime(
			Map<Method, JPLRetentionTime> specificRetentionTime) {
		this.specificRetentionTime = specificRetentionTime;
	}

	/**
	 * @return the contributor
	 */
	public Contributor getContributor() {
		return contributor;
	}

	/**
	 * @param contributor the contributor to set
	 */
	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	/**
	 * @return the instrument
	 */
	public Instrument getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the references
	 */
	public SortedSet<Reference> getReferences() {
		return references;
	}

	/**
	 * @param references the references to set
	 */
	public void setReferences(SortedSet<Reference> references) {
		this.references = references;
	}

	/**
	 * @return the precurcor
	 */
	public JPLExpMSPeakLC getPrecurcor() {
		return precurcor;
	}

	/**
	 * @param precurcor the precurcor to set
	 */
	public void setPrecurcor(JPLExpMSPeakLC precurcor) {
		this.precurcor = precurcor;
	}
	
}
