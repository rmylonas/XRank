/**
 *
 */
package xrank.ms.scoring.statistics.normalization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xrank.ms.match.SpSpMatch;
import xrank.ms.match.SpSpMatchList;


/**
 * @author Roman Mylonas
 * 
 */

public class MultipleCurvesRNormalizator implements Normalizator {

	static int count_static = 0;
	protected Log currentLogger = null;
	protected SpSpMatchList matchList;
//	protected RConnection c;
	protected String currentWorkPath = null;

	public MultipleCurvesRNormalizator() {

		this.setCurrentLogger(LogFactory.getLog(this.getClass()));
	}

	protected void finalize() {
//		c.close();
	}


	public void normalizeList() {

		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();

		double[] scoreList = new double[this.matchList.getMatchList().size()];

		int count = 0;

		while (matchIt.hasNext()) {
			SpSpMatch match = matchIt.next();
			
			//TODO if the normalisation is concluent, find a way to apply a normalization for each scoringValue
			double x = match.getScoringValue().get(0).getScore();
			scoreList[count] = x;
			count++;
		}

		// try{
		// c = new RConnection();
		// c.assign("scores",scoreList);
		// c.eval("save(scores, file=\"/tmp/dta/scores-" + count_static++ +
		// ".dta\");");
		// }catch(Exception e){
		// e.printStackTrace();
		// }

		if (scoreList.length > 20) {

			Arrays.sort(scoreList);

			double min = scoreList[0];
			currentLogger.info("score size : " + scoreList.length);
			currentLogger.info("min : " + min);

			// Create the cumulative distribution
			double previousValue = -1000000;
			int valCount = 0;
			ArrayList<Double> x = new ArrayList<Double>();
			ArrayList<Double> y = new ArrayList<Double>();

			// remove the min scores
			ArrayList<Double> tmpList = new ArrayList<Double>();
			ArrayList<Double> ytmpList = new ArrayList<Double>();
			ArrayList<Double> yTmp = new ArrayList<Double>();

			for (double val : scoreList) {
				if (val > min) {
					tmpList.add(val);
				}
			}

			int effectif = tmpList.size();

			for (double val : tmpList) {

				if (val != previousValue) {

					if (x.size() > 0) {
						y.add(Math.log(1 - ((double) valCount / effectif)));
						ytmpList.add(((double) valCount / effectif));

						// Debugging
						currentLogger.debug("raw y value : " + valCount);
						currentLogger
								.debug("y value before log : "
										+ String
												.valueOf(((double) valCount / effectif)));
						currentLogger.debug("y value : "
								+ Math.log(1 - ((double) valCount / effectif)));

					}
					x.add(val - min);
				}

				previousValue = val;
				valCount++;
			}

			ytmpList.add(((double) valCount / effectif));

			// ytmpList.remove(ytmpList.size()-1);
			// x.remove((x.size() - 1));

			ArrayList<Double> xTmp = x;

			yTmp = ytmpList;

			// x.remove((x.size() - 1)); // Remove the last item

			currentLogger.info("cumulative size : " + x.size());

			// Point sampling
			double ythres = -4;
			double yUpthres = -0.5;

			ArrayList<Integer> idx = new ArrayList<Integer>();

			for (int j = 0; j < y.size(); j++) {
				if (y.get(j) >= ythres && y.get(j) <= yUpthres) {
					idx.add(j);
				}
			}

			currentLogger.info(idx.size() + " pass the y threshold of "
					+ ythres + " and " + yUpthres);

			double[] xRescaled = new double[idx.size()];
			double[] yRescaled = new double[idx.size()];

			int countPlace = 0;
			for (int subIdx : idx) {
				xRescaled[countPlace] = x.get(subIdx);
				yRescaled[countPlace] = y.get(subIdx);
				countPlace++;
			}

			int k = 1;
			idx = new ArrayList<Integer>();
			while (k <= yRescaled.length) {
				idx.add(yRescaled.length + 1 - k);
				k = (int) Math.floor(1 + k * 1.2);

			}

			currentLogger.info(idx.size() + " elements after sampling");

			ArrayList<Double> xSampled = new ArrayList<Double>();
			ArrayList<Double> ySampled = new ArrayList<Double>();
			for (int subIdx : idx) {
				if (xRescaled[subIdx - 1] > 0 && yRescaled[subIdx - 1] < -1) {
					xSampled.add(xRescaled[subIdx - 1]);
					ySampled.add(yRescaled[subIdx - 1]);

					currentLogger.debug("x value : " + xRescaled[subIdx - 1]);
					currentLogger.debug("y value : " + yRescaled[subIdx - 1]);
				}
			}

			currentLogger.debug(xSampled.size()
					+ " elements pass the thresholds of x>0 and y<-1");

			// Compute m1 et m2
			double[] z = new double[ySampled.size()];
			for (int i = 0; i < xSampled.size(); i++) {
				z[i] = ySampled.get(i) / xSampled.get(i);

			}

			double[] zCopy = z.clone();

			double sum1 = 0.0;
			double sum2 = 0.0;
			for (int i = 0; i < xSampled.size(); i++) {
				sum1 -= ((double) ((double) xSampled.get(i) - median(xSampled)) * ((double) z[i] - median(zCopy)));

				currentLogger
						.debug("som1 first term value : "
								+ ((double) ((double) xSampled.get(i) - median(xSampled))));
				currentLogger.debug("som1 second term value : " + z[i] + " - "
						+ median(zCopy) + " = "
						+ ((double) z[i] - median(zCopy)));
				currentLogger
						.debug("som1 value : "
								+ ((double) ((double) xSampled.get(i) - median(xSampled)) * ((double) z[i] - median(zCopy))));
			}

			currentLogger.info("median x : " + median(xSampled));
			currentLogger.info("median z : " + median(zCopy));
			currentLogger.info("sum 1 : " + sum1);

			for (int i = 0; i < xSampled.size(); i++) {
				sum2 -= Math.pow((xSampled.get(i) - median(xSampled)), 2);
			}
			currentLogger.info("sum 2 : " + sum2);

			double m1 = 0.0;
			double m2 = 0.0;

			m2 = ((double) -1 * sum1 / sum2);
			m1 = ((double) -1 * (median(zCopy) + m2 * median(xSampled)));

			currentLogger.info("m1 : " + m1 + " m2 : " + m2);
			if (m2 < 0) {
				m2 = 0;
				m1 = -median(zCopy);
				currentLogger.info("rescaled : m1 : " + m1 + " m2 : " + m2);
			}

			// Compute xthres
			double epsilon = Math.exp(-20);
			double xthres = 0.0;
			if (Math.abs(m2) < epsilon) {
				xthres = (-ythres) / m1;
			} else {
				xthres = (-m1 + Math.sqrt(Math.pow(m1, 2) + 4 * (-ythres) * m2))
						/ (2 * m2);
			}
			currentLogger.info("xthres : " + xthres);

			// //Compute lin corr coef
			idx = new ArrayList<Integer>();

			for (int i = 0; i < x.size() - 1; i++) {
				currentLogger.debug("xdelta[i] value : " + x.get(i));
				if (x.get(i) > xthres) {
					idx.add(i);
				}
			}

			currentLogger.info(idx.size() + " elements pass the threshold");

			if (idx.size() > 0) {
				double[] ydelta = new double[idx.size()];
				double[] xdeltaFinal = new double[idx.size()];
				count = 0;

				for (int place : idx) {

					ydelta[count] = y.get(place);
					xdeltaFinal[count] = xTmp.get(place + 1);

					count++;
				}

				double[] deltaX;
				double[] deltaY;

				currentLogger.info(xdeltaFinal.length
						+ " elements pass the threshold");

				// if (xdeltaFinal.length > 5) {

				k = 1;
				idx = new ArrayList<Integer>();
				while (k <= xdeltaFinal.length) {
					idx.add(((int) xdeltaFinal.length + 1 - k));
					k = (int) Math.floor(1 + k * 1.2);
				}

				double[] deltaXTmp = new double[idx.size()];
				double[] deltaYTmp = new double[idx.size()];

				count = 0;
				for (int place : idx) {
					deltaXTmp[count] = xdeltaFinal[place - 1];

					currentLogger.debug("deltaX value : " + deltaXTmp[count]);

					deltaYTmp[count] = ydelta[place - 1];
					currentLogger.debug("deltaY value : " + deltaYTmp[count]);

					count++;
				}

				deltaX = deltaXTmp;
				deltaY = deltaYTmp;

				deltaYTmp = doNormalization(deltaX, m1, m2, 100000.0, 0.0);

				count = 0;
				for (double val : deltaYTmp) {
					deltaY[count] = deltaY[count] - val;
					currentLogger.debug("deltaY value : " + deltaYTmp[count]);
					count++;
				}

				idx = new ArrayList<Integer>();
				for (int i = 0; i < deltaX.length; i++) {

					currentLogger.debug("delta x value : " + deltaX[i]);
					if (deltaX[i] > xthres) {
						idx.add(i);
					}
				}

				currentLogger.info(idx.size() + " elements pass the threshold");

				double[] ratio = new double[idx.size()];
				count = 0;
				for (int i : idx) {
					ratio[count] = (deltaY[i] / (deltaX[i] - xthres));
					currentLogger.info("compute : " + deltaY[i] + " / ("
							+ deltaX[i] + "-" + xthres + ")");
					currentLogger.debug("ratio value : " + ratio[count]);
					count++;
				}

				double linCor = median(ratio);
				if (linCor > (m1 + 2 * m2 * xthres)) {
					linCor = m1 + 2 * m2 * xthres;
				}

				currentLogger.info("linear coeff : " + linCor);

				doNormalization(min, m1, m2, xthres, linCor);

			} else {
				currentLogger
						.warn("too few results, not normalized scores will be chosen.");
				copyScores();
			}
		} else {

			copyScores();
		}

	}

	public Iterator<SpSpMatch> doNormalization(double min, double l1,
			double l2, double thres, double linCor) {

		Iterator<SpSpMatch> matchs = this.matchList.getMatchList().iterator();

		while (matchs.hasNext()) {
			SpSpMatch match = matchs.next();

			double score = ((double) -l1 * match.getScoringValue().get(0).getScore() - l2
					* Math.pow(match.getScoringValue().get(0).getScore(), 2));

			if (score > thres) {
				match.getScoringValue().get(0).setZScore(
						(-1 * ((double) score + linCor * (score - thres))));
			} else {
				match.getScoringValue().get(0).setZScore(((double) -1 * score));
			}
		}

		return matchs;

	}

	public double[] doNormalization(double[] scores, double l1, double l2,
			double thres, double linCor) {

		double[] scoresOut = new double[scores.length];

		int count = 0;
		for (double val : scores) {

			double score = -l1 * val - l2 * Math.pow(val, 2);

			if (score > thres) {
				scoresOut[count] = ((double) score + linCor * (score - thres));

			} else {
				scoresOut[count] = score;
			}

			count++;
		}

		return scoresOut;

	}

	public Iterator<SpSpMatch> copyScores() {

		Iterator<SpSpMatch> matchs = this.matchList.getMatchList().iterator();

		while (matchs.hasNext()) {
			SpSpMatch match = matchs.next();

			match.getScoringValue().get(0).setZScore(
					match.getScoringValue().get(0).getScore());
		}

		return matchs;

	}

	public double median(double[] values) {
		Arrays.sort(values);
		int midIndex = (values.length / 2);
		double median;
		if (midIndex % 2 == 0) {
			median = (double) values[midIndex]; // array index starts at 0
		} else {
			median = ((double) (values[midIndex - 1] + values[midIndex])) / 2.0;

		}
		return median;

	}

	public double median(ArrayList<Double> valuesTmp) {

		double[] values = new double[valuesTmp.size()];
		int count = 0;
		for (double val : valuesTmp) {
			values[count] = val;
			count++;
		}

		Arrays.sort(values);
		int midIndex = values.length / 2;
		double median;
		if (midIndex % 2 == 0) {
			median = (double) values[midIndex]; // array index starts at 0
		} else {
			median = ((double) (values[midIndex - 1] + values[midIndex])) / 2.0;

		}
		return median;

	}

	public SpSpMatchList getMatchList() {
		return matchList;
	}

	public void setMatchList(SpSpMatchList matchList) {
		this.matchList = matchList;
	}

	public Log getCurrentLogger() {
		return currentLogger;
	}

	public void setCurrentLogger(Log currentLogger) {
		this.currentLogger = currentLogger;
	}

}
