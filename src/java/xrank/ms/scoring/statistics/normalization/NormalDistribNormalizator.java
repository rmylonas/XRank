/**
 * 
 */
package xrank.ms.scoring.statistics.normalization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xrank.ms.match.SpSpMatch;


/**
 * @author roman
 * 
 */

public class NormalDistribNormalizator extends MatchListNormalizatorBase
		implements Normalizator {

	protected Log currentLogger = null;

	protected Double mean = null;
	protected Double stddev = null;

	protected Double randomScores = null;
	protected Integer nrOfRandom = null;

	public NormalDistribNormalizator() {
		this.currentLogger = LogFactory.getLog(this.getClass());
	}


	public void normalizeList() {

		// has to calculate it anyway
		// if(this.mean == null || this.stddev == null){
		this.computeDistrib();
		// }

		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();

		while (matchIt.hasNext()) {
			SpSpMatch match = matchIt.next();

			double x = match.getScoringValue().get(0).getScore();
			double zScore = this.calcZScore(x, this.mean, this.stddev);

			match.getScoringValue().get(0).setZScore(zScore);
			match.getScoringValue().get(0).setPValue(this.calcPValue(zScore));
		}

	}

	protected double calculateY(final double x, final double mean,
			final double stddev) {
		// double normX = 1/(this.stddev * Math.sqrt(2*Math.PI)) * Math.exp(-1*
		// Math.pow(x-this.mean, 2)/(2*Math.pow(this.stddev, 2)));

		return 1
				/ (stddev * Math.sqrt(2 * Math.PI))
				* Math.exp(-1 * Math.pow(x - mean, 2)
						/ (2 * Math.pow(stddev, 2)));
	}

	protected double calcZScore(final double x, final double mean,
			final double stddev) {
		return (x - mean) / stddev;
	}

	public double calcPValue(final double zScore) {
		double zValue = this.calcZValue(zScore);
		int n = this.matchList.getNr();
		return (n * zValue >= 1) ? 1 : ((n * zValue < 0.001) ? (n * zValue)
				: (1 - Math.pow(1 - zValue, n)));
	}

	public double calcZValue(final double zScore) {
		double[] p = { 220.2068679123761, 221.2135961699311, 112.0792914978709,
				33.91286607838300, 6.373962203531650, .7003830644436881,
				.03526249659989109 };

		double[] q = { 440.4137358247522, 793.8265125199484, 637.3336333788311,
				296.5642487796737, 86.78073220294608, 16.06417757920695,
				1.755667163182642, .08838834764831844 };

		double cutOff = 7.7071;
		double root2Pi = 2.506628274631001;

		double zabs = Math.abs(zScore);
		if (zScore > 37.)
			return 0.;
		if (zScore < -37)
			return 1;

		double expntl = Math.exp(-.5 * zabs * zabs);
		double pdf = expntl / root2Pi;

		double tail;
		if (zabs < cutOff) {
			tail = expntl
					* ((((((p[6] * zabs + p[5]) * zabs + p[4]) * zabs + p[3])
							* zabs + p[2])
							* zabs + p[1])
							* zabs + p[0])
					/ (((((((q[7] * zabs + q[6]) * zabs + q[5]) * zabs + q[4])
							* zabs + q[3])
							* zabs + q[2])
							* zabs + q[1])
							* zabs + q[0]);

		} else {
			tail = pdf
					/ (zabs + 1. / (zabs + 2. / (zabs + 3. / (zabs + 4. / (zabs + 0.65)))));

		}
		return (zScore > 0) ? (double) tail : (double) (1 - tail);

	}

	/**
	 * Compute distribution parameters (mean, stddev, ..)
	 */
	public void computeDistrib() {
		double sum = 0;

		/* the mean */
		// Iterator<SpSpMatch> matchIt =
		// this.matchList.getMatchList().iterator();
		// while(matchIt.hasNext()){
		// SpSpMatch match = matchIt.next();
		// sum += match.getScoringValue().getScore();
		for (Double score : this.matchList.getHistoryScores()) {
			sum += score;
		}

		this.mean = sum / matchList.getHistoryScores().size();

		/* the stddev */
		double quadsum = 0;

		// matchIt = this.matchList.getMatchList().iterator();

		// while(matchIt.hasNext()){
		// SpSpMatch match = matchIt.next();
		// quadsum += Math.pow(match.getScoringValue().getScore() -this.mean,
		// 2);

		for (Double score : this.matchList.getHistoryScores()) {
			quadsum += Math.pow(score - this.mean, 2);
		}

		// this.stddev = Math.sqrt(quadsum /
		// this.matchList.getMatchList().size());
		this.stddev = Math.sqrt(quadsum
				/ this.matchList.getHistoryScores().size());
	}


	public String histogramString() {

		String s = "";

		double[][] histogram = this.computeHistogram();

		for (double[] onePair : histogram) {
			s += String.valueOf(onePair[0]) + "\t" + String.valueOf(onePair[1])
					+ "\n";
		}

		return s;

	}


	public String theoreticalHistogramString() {

		String s = "";

		double[][] theoreticalHistogram = this.computeTheoreticalHistogram();

		for (double[] onePair : theoreticalHistogram) {
			s += String.valueOf(onePair[0]) + "\t" + String.valueOf(onePair[1])
					+ "\n";
		}

		return s;

	}


	public double[][] histogram() {

		double[][] histogram = this.computeHistogram();

		return histogram;
	}

	/**
	 * Calculate the histogram considering the current mean and stddev.
	 * 
	 * @return
	 */
	protected double[][] computeHistogram() {
		double[][] histo = new double[this.matchList.getMatchList().size()][2];

		if (this.mean == null || this.stddev == null) {
			this.computeDistrib();
		}

		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();

		int i = 0;

		while (matchIt.hasNext()) {
			// for(int i=0; i<this.matchList.size(); i++){

			SpSpMatch match = matchIt.next();
			// for(int i=0; i<this.matchList.size(); i++){
			double x = match.getScoringValue().get(0).getScore();
			histo[i][0] = x;
			histo[i][1] = this.calculateY(x, this.mean, this.stddev);
			i++;
		}

		return histo;
	}


	public double[][] theoreticalHistogram() {

		double[][] theoreticalHistogram = this.computeTheoreticalHistogram();

		return theoreticalHistogram;

	}

	/**
	 * Calculate a random histogram considering the current mean, stddev and
	 * size of the matchList.
	 * 
	 * @return
	 */
	protected double[][] computeTheoreticalHistogram() {

		/* set the nrOfRandom to the same size as the matchList */
		if (this.nrOfRandom == null) {
			this.nrOfRandom = this.matchList.getMatchList().size();
		}

		this.currentLogger.debug("nrOfRandom: " + this.nrOfRandom);

		double[][] histo = new double[this.nrOfRandom][2];

		if (this.mean == null || this.stddev == null) {
			this.computeDistrib();
		}

		this.currentLogger.debug("mean: " + this.mean);
		this.currentLogger.debug("stddev: " + this.stddev);

		double[] randomX = this.generateRandom(this.nrOfRandom);

		int i = 0;
		for (double x : randomX) {
			histo[i][0] = x;
			histo[i][1] = this.calculateY(x, this.mean, this.stddev);

			this.currentLogger.debug("one theo pair: " + x + "\t"
					+ this.calculateY(x, this.mean, this.stddev));

			i++;
		}

		return histo;
	}


	public double[] generateRandom(final int n) {
		Random generator = new Random();
		double[] values = new double[n];

		for (int i = 0; i < n; i++) {
			values[i] = (generator.nextGaussian() * this.stddev) + this.mean;

			this.currentLogger.debug("random values: " + values[i]);
		}

		return values;
	}


	public double generateRandom() {
		Random generator = new Random();

		return (generator.nextGaussian() * this.stddev) + this.mean;

	}

	/**
	 * @return the mean
	 */
	public Double getMean() {
		return mean;
	}

	/**
	 * @param mean
	 *            the mean to set
	 */
	public void setMean(Double mean) {
		this.mean = mean;
	}

	/**
	 * @return the stddev
	 */
	public Double getStddev() {
		return stddev;
	}

	/**
	 * @param stddev
	 *            the stddev to set
	 */
	public void setStddev(Double stddev) {
		this.stddev = stddev;
	}

	/**
	 * @return the nrOfRandom
	 */
	public Integer getNrOfRandom() {
		return nrOfRandom;
	}

	/**
	 * @param nrOfRandom
	 *            the nrOfRandom to set
	 */
	public void setNrOfRandom(Integer nrOfRandom) {
		this.nrOfRandom = nrOfRandom;
	}

	public void setAdditionalScores(ArrayList scores) {
		// TODO Auto-generated method stub

	}

}
