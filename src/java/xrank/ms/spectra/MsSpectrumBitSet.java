package xrank.ms.spectra;

import java.util.BitSet;

/**
 * The conversion from an MS-spectrum into a BitSet for faster calculations
 * 
 * @author roman
 * 
 */

public class MsSpectrumBitSet extends BitSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1927138109165073154L;

	protected double binSizeDalton = 1;
	protected int maxNrOfPeaks = 30;
	protected double lowestMozDalton = 40;

//	protected Log currentLogger = null;

	public MsSpectrumBitSet() {
	}

	public MsSpectrumBitSet(double[] masses) {
//		this.currentLogger = LogFactory.getLog(this.getClass());
		this.calculateMsSpectrumBitSet(masses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.BitSet#toString()
	 */
	public String toString() {
		String localString = new String();

		for (int i = 0; i < this.length(); i++) {
			localString += String.valueOf(i + lowestMozDalton) + "\t"
					+ String.valueOf(this.get(i)) + "\n";
		}

		return localString;
	}

	/**
	 * converts a MsSpectrum into a MsSpectrumBitSet using the setted parameters
	 * 
	 * @param aSpectrum
	 */
	protected void calculateMsSpectrumBitSet(double[] masses) {
//		this.currentLogger.debug("calculate BitSet");

		for (double val : masses) {

			double position = (val - lowestMozDalton) / (binSizeDalton);
			int roundedPosition = (int) Math.round(position);
//			
//			BigDecimal bd = new BigDecimal(position);
//			int roundedPosition  = bd.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		    
			if (roundedPosition > 0) {
				this.set(roundedPosition);
//				this.currentLogger.debug("setted: " + roundedPosition);
			}
		}

	}

	/**
	 * @return the binSizeDalton
	 */
	public double getBinSizeDalton() {
		return binSizeDalton;
	}

	/**
	 * @param binSizeDalton
	 *            the binSizeDalton to set
	 */
	public void setBinSizeDalton(double binSizeDalton) {
		this.binSizeDalton = binSizeDalton;
	}

	/**
	 * @return the lowestMozDalton
	 */
	public double getLowestMozDalton() {
		return lowestMozDalton;
	}

	/**
	 * @param lowestMozDalton
	 *            the lowestMozDalton to set
	 */
	public void setLowestMozDalton(double lowestMozDalton) {
		this.lowestMozDalton = lowestMozDalton;
	}

	/**
	 * @return the maxNrOfPeaks
	 */
	public int getMaxNrOfPeaks() {
		return maxNrOfPeaks;
	}

	/**
	 * @param maxNrOfPeaks
	 *            the maxNrOfPeaks to set
	 */
	public void setMaxNrOfPeaks(int maxNrOfPeaks) {
		this.maxNrOfPeaks = maxNrOfPeaks;
	}
}
