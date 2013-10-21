package org.expasy.jpl.experimental.ms.lcmsms.filtering.filter;

import org.expasy.jpl.experimental.ms.lcmsms.filtering.JPLSpectrumFilteringResult;
import org.expasy.jpl.experimental.ms.lcmsms.filtering.JPLSpectrumOnePeakPerWindowFilter;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.utils.sort.SimpleTypeArray;

public class JPLSpectrumOnePeakPerWindowFilterLocal extends
	JPLSpectrumOnePeakPerWindowFilter {
	
	private int threshold = 30;
	
	
	@Override
	public JPLFragmentationSpectrum filterSpectrum(JPLFragmentationSpectrum inputSpectrum) {
		

		int [] indexesA = SimpleTypeArray.sortIndexesDown(inputSpectrum.getIntensities());
		double[] mzsTmp = inputSpectrum.getMzs().clone();

		//Count the number of eliminated peaks
		int invalidValuesCount = 0;
		for(int i : indexesA){

			double originalMass = mzsTmp[i] ;

			int[] interval = inputSpectrum.getMassIndexesBetween(mzsTmp[i] - level, mzsTmp[i] + level);
			if (interval[0] != -1){
				for(int j = interval[0]; j<=interval[1]; j++){
					mzsTmp[j] = -1000.0;
				}
			}

			mzsTmp[i] = originalMass;
			

		}

		//count the eliminated peaks
		for(double val : mzsTmp){
			if(val == -1000.0)
				invalidValuesCount++;
		}

		//Reconstruct arrays of intensities and mzs without eliminated peaks
		double[] intensities = new double[(mzsTmp.length - invalidValuesCount)];
		double[] mzs = new double[(mzsTmp.length - invalidValuesCount)];

		int addCount = 0;

		for(int i = 0; i<mzsTmp.length; i++ ){
			if (mzsTmp[i] != -1000.0 && addCount < mzs.length){
				mzs[addCount] = mzsTmp[i];
				intensities[addCount] = inputSpectrum.getIntensityAt(i);
				addCount++;
			}
		}
		
		
		int [] indexesMostIntense = SimpleTypeArray.sortIndexesDown(intensities);
		double[] intensitiesFinal;
		double[] mzsFinal;
		
		
		if(indexesMostIntense.length > threshold){
			double[] intensitiesFinalTmp =  new double[threshold];
			double[] mzsFinalTmp = new double[threshold];
			
			for(int j=0; j<threshold; j++){
				intensitiesFinalTmp[j] = intensities[indexesMostIntense[j]];
				mzsFinalTmp[j] = mzs[indexesMostIntense[j]];
			}
			
			int [] indexesMasses = SimpleTypeArray.sortIndexesUp(mzsFinalTmp);
			
			intensitiesFinal =  new double[threshold];
			mzsFinal = new double[threshold];
			
			for(int k=0; k<threshold; k++){
				intensitiesFinal[k] = intensitiesFinalTmp[indexesMasses[k]];
				mzsFinal[k] = mzsFinalTmp[indexesMasses[k]];
			}
		
			
		}else{
			
			intensitiesFinal = intensities;
			mzsFinal = mzs;
		}
		
		
		//reconstruct the output spectrum
		JPLFragmentationSpectrum outputSpectrum = new JPLFragmentationSpectrum(mzsFinal, intensitiesFinal);

		//Attach the filtering information
		outputSpectrum.setFilteringResult(new JPLSpectrumFilteringResult(inputSpectrum, this));

		
		
		
		return outputSpectrum;
	}

}
