/**
 *
 */
package xrank.ms.scoring.results;

import java.util.Map;

import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

import xrank.ms.match.SpSpMatchList;


/**
 * Contains an Map containing the experimental-spectra {@link MsSpectrum} as
 * keys and a sorted and purified {@link SpSpMatchList} as values.
 * 
 * Every class IdResult contains data, but doesn't calculate anything.
 * 
 * @author roman
 * 
 */

public class IdResults {

	protected Map<JPLFragmentationSpectrum, SpSpMatchList> spSpMatches;

	public Map<JPLFragmentationSpectrum, SpSpMatchList> getSpSpMatches() {
		return spSpMatches;
	}

	public void setSpSpMatches(
			Map<JPLFragmentationSpectrum, SpSpMatchList> spSpMatches) {
		this.spSpMatches = spSpMatches;
	}

}
