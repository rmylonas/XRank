/**
 * 
 */
package xrank.ms.scoring.statistics.normalization;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xrank.ms.match.SpSpMatch;
import xrank.ms.match.SpSpMatchList;


/**
 * @author roman
 * 
 */

public class LogNormalizator implements Normalizator {

	protected Log currentLogger = null;

	protected SpSpMatchList matchList;
	protected double threshold;
	protected double base = -8.255054;

	public LogNormalizator() {
		this.currentLogger = LogFactory.getLog(this.getClass());
	}


	public void normalizeList() {

		double sum = 0.0;
		int nb = 0;

		Iterator<SpSpMatch> matchIt = this.matchList.getMatchList().iterator();

		while (matchIt.hasNext()) {
			SpSpMatch match = matchIt.next();

			double x = match.getScoringValue().get(0).getScore();

			if (x > threshold) {
				sum = sum + x - (-base + threshold) - base;
				nb++;
			}

		}

		double average = sum / nb;

		matchIt = this.matchList.getMatchList().iterator();

		while (matchIt.hasNext()) {
			SpSpMatch match = matchIt.next();

			double x = match.getScoringValue().get(0).getScore();

			if (x > (-base + threshold) + base) {
				match.getScoringValue().get(0).setZScore(
						(x - base - (-base + threshold)) / average);
			} else {
				match.getScoringValue().get(0).setZScore(0.0);
			}
		}

	}

	public SpSpMatchList getMatchList() {
		return this.matchList;
	}

	public void setMatchList(SpSpMatchList spML) {
		this.matchList = spML;

	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public void setAdditionalScores(ArrayList scores) {
		// TODO Auto-generated method stub

	}

}
