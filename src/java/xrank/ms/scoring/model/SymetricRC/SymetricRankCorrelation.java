/**
 *
 */
package xrank.ms.scoring.model.SymetricRC;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;

import xrank.ms.match.ScoringValue;
import xrank.ms.scoring.model.ScoringModel;
import xrank.ms.scoring.model.ScoringModelBase;
import xrank.ms.scoring.model.RC.parameters.RCParams;


/**
 * Calculates a matching-score between two spectra based on the probabilities of
 * the ranks which matches.
 * 
 * @author Roman Mylonas
 * 
 */
public class SymetricRankCorrelation extends ScoringModelBase implements
		ScoringModel {

	// protected RankCorrelationReader reader;
	protected Log currentLogger;
	protected RCParams params;
	protected int nrOfRanks;
	protected int nrOfPeaks;
	protected double matchTolerance;
	
	public SymetricRankCorrelation() {
		currentLogger = LogFactory.getLog(this.getClass());
	}

	public ScoringValue generateScore(JPLFragmentationSpectrum spA,
			JPLFragmentationSpectrum spB, int[] indexesA, int[] indexesB) {

		nrOfRanks = this.params.getNrOfRanks();
		nrOfPeaks = this.params.getNrOfPeaks();
		matchTolerance = this.params.getMatchTolerance();
		
		int[] indexesBTransposed = new int[indexesB.length];
		int[] indexesATransposed = new int[indexesA.length];

		int increment = 0;
		for (int val : indexesA) {
			indexesATransposed[val] = increment;
			increment++;
		}

		increment = 0;
		for (int val : indexesB) {
			indexesBTransposed[val] = increment;
			increment++;
		}

		int[] alignment = new int[nrOfRanks];
		int[] revAlignment = new int[nrOfRanks];

		// fill the arrays with -1
		for (int k = 0; k < nrOfRanks; k++) {
			alignment[k] = -1;
			revAlignment[k] = -1;
		}

		// Forward alignment
		int rankCounter = 0;
		
		for (int rank : indexesA) {
			
			
			if (rankCounter >= nrOfRanks)
				continue;
			
//			System.out.println("----------------------------------retained peak : "+spA.getMassAt(rank));
			

			// Retrieve the mass according to the rank
			double upperLimit = spA.getMassAt(rank)
					+ matchTolerance;
			double lowerLimit = spA.getMassAt(rank)
					- matchTolerance;

			// As the spectrum is filtered, this query should return only one
			// result
			int[] matchIdx = spB.getMassIndexesBetween(lowerLimit, upperLimit);
			
			
			if (matchIdx[0] != matchIdx[1]) {

				if(! (indexesBTransposed[matchIdx[0]] < indexesBTransposed[matchIdx[1]])){
					matchIdx[0] = matchIdx[1];
				} 
			}

			// Case where there is a match
			
			
			if (matchIdx[0] != -1 && indexesBTransposed[matchIdx[0]] <= nrOfPeaks) {

				alignment[rankCounter] = indexesBTransposed[matchIdx[0]];
				

			} else {
				alignment[rankCounter] = -1;
			}

			rankCounter++;

		}

		// reverse alignment
		rankCounter = 0;
		for (int rank : indexesB) {
			if (rankCounter >= nrOfRanks)
				continue;

			// Retrieve the mass according to the rank
			double upperLimit = spB.getMassAt(rank)
					+ matchTolerance;
			double lowerLimit = spB.getMassAt(rank)
					- matchTolerance;

			// As the spectrum is filtered, this query should return only one
			// result
			int[] matchIdx = spA.getMassIndexesBetween(lowerLimit, upperLimit);

			if (matchIdx[0] != matchIdx[1]) {
				
				if(! (indexesATransposed[matchIdx[0]] < indexesATransposed[matchIdx[1]])){
					matchIdx[0] = matchIdx[1];
				} 
			}

			// Case where there is a match
			if (matchIdx[0] != -1 && indexesATransposed[matchIdx[0]] <= nrOfPeaks) {
				revAlignment[rankCounter] = indexesATransposed[matchIdx[0]];

			} else {
				revAlignment[rankCounter] = -1;
			}

			rankCounter++;

		}

		double score = getScoreFromArray(alignment, revAlignment);

		/* put the results into the match object */
		ScoringValue scoringValue = new ScoringValue();
		scoringValue.setScore((score / 2));
		scoringValue.setZScore(scoringValue.getScore());

//		currentLogger.info("final-score: " + scoringValue.getScore());

		return scoringValue;

	}

	private double getScoreFromArray(int[] alignment, int[] revAlignment) {
		double score = 0.0;
		int counter = 0;
//		int nbOfPeaksInCommon = 0;
		
		List<List<Double>> probList = this.params.getProbabilities();

		currentLogger.debug("alignement: ");

		for (int i : alignment) {
			
			List<Double> probSubList = probList.get(counter);

			Double oneScore;

			if (i > -1) {

				if (i < nrOfPeaks) {
					oneScore = probSubList.get(
							i);
//					currentLogger.debug("i: " + i + " and " + counter
//							+ " : oneScore: " + oneScore);
				} else {
					oneScore = 0.0;
//					currentLogger.debug("i: " + i + " and " + counter
//							+ " : oneScore: " + oneScore);
				}
//				nbOfPeaksInCommon++;
			} else {

				oneScore = probSubList.get(nrOfPeaks);
//				currentLogger.debug("i: " + i + " and " + counter
//						+ " : oneScore: " + oneScore);
			}

			score += oneScore;

			counter++;
		}

//		currentLogger.debug("alignment-score : " + score);

		counter = 0;

//		currentLogger.debug("rev-alignmengt: ");

		for (int i : revAlignment) {
			
			List<Double> probSubList = probList.get(counter);

			Double oneScore;

			if (i > -1) {

				if (i < nrOfPeaks) {
					oneScore = probSubList.get(
							i);
//					currentLogger.debug("i: " + i + " and " + counter
//							+ " : oneScore: " + oneScore);
					
				} else {
					oneScore = 0.0;
//					currentLogger.debug("i: " + i + " and " + counter
//							+ " : oneScore: " + oneScore);
				}

//				nbOfPeaksInCommon++;

			} else {

				oneScore = probSubList.get(nrOfPeaks);
//				currentLogger
//						.debug("i: " + i + " : oneScore: " + oneScore);
			}

			score += oneScore;

			counter++;

		}

		return score;

	}

	public RCParams getParams() {
		return params;
	}

	public void setParams(RCParams params) {
		this.params = params;
	}

	public Float getMozThershold() {
		return this.params.getMatchTolerance().floatValue();
	}

	public Integer getNrOfPeaks() {
		return this.params.getNrOfPeaks();
	}

}
