/**
 * Contains a LC msms precursor + MS(n) peaklist
 * such a spectra must threfore contain its msLevel (>=2)
 *  @author alex masselot
 */
package org.expasy.jpl.experimental.ms.peaklist;

import java.util.List;

import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime.RTUnit;
import org.expasy.jpl.experimental.ms.lcmsms.filtering.JPLSpectrumFilteringResult;
import org.expasy.jpl.experimental.ms.lcmsms.filtering.JPLSpectrumHasBeenFiltered;
import org.expasy.jpl.experimental.ms.peak.JPLExpMSPeakLC;


public class JPLFragmentationSpectrum extends JPLRunLCPeaklist 
	implements JPLSpectrumHasBeenFiltered {

	private static final long serialVersionUID = 5278186777105059360L;

	private JPLExpMSPeakLC precursor;
	private int msLevel;
	private JPLSpectrumFilteringResult filteringResult;
	

	protected boolean valid;
	
	public JPLFragmentationSpectrum() { 
		this.valid = true;
	}
	
	public JPLFragmentationSpectrum(JPLFragmentationSpectrum src, List<Integer> indexes) {
		super(src, indexes);
		this.valid = true;
	}

	public JPLFragmentationSpectrum(double[] masses, double[] intensities) {
		super(masses, intensities);
		this.valid = true;
	}

	public JPLFragmentationSpectrum clone() {
		JPLFragmentationSpectrum pl = (JPLFragmentationSpectrum) super.clone();
		
		pl.id = this.id;
		pl.precursor = precursor;
		pl.msLevel = this.msLevel;
		
		return pl;
	}
	
	public JPLExpMSPeakLC getPrecursor() {
		return precursor;
	}

	public void setPrecursor(JPLExpMSPeakLC precursor) {
		this.precursor = precursor;
	}

	public JPLRetentionTime getRetentionTime() {
		return precursor.getRetentionTime();
	}

	public void setRetentionTime(final double rt, final RTUnit unit){
		getPrecursor().getRetentionTime().setValue(rt);
		getPrecursor().getRetentionTime().setUnit(unit);
	}

	public JPLSpectrumFilteringResult getFilteringResult() {
		return filteringResult;
	}

	public void setFilteringResult(JPLSpectrumFilteringResult jPLFilteringResult) {
		filteringResult = jPLFilteringResult;
	}

	public int getMsLevel() {
		return msLevel;
	}

	public void setMsLevel(int msLevel) {
		this.msLevel = msLevel;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
