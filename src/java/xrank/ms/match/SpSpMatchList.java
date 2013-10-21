/* 
 *	%W% %E% Roman Mylonas
 *  
 */

package xrank.ms.match;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Stores a list of matches that are related. But also stores the scores of
 * matches that are not recorded and even the number of times a spectra has
 * found a candidate
 * 
 * @author roman
 * 
 */

public class SpSpMatchList {

	/**
	 * 
	 */

	protected Boolean sorted = false;
	protected ArrayList<Double> historyScores = new ArrayList<Double>();
	protected int nr;
	protected SortedSet<SpSpMatch> matchList = new TreeSet<SpSpMatch>();

	public SpSpMatchList() {
	}

	/**
	 * Adds an {@link SpSpMatch}-element, the corresponding score and
	 * increments the nr.
	 * 
	 * @param sp
	 *            the {@link SpSpMatch} to add
	 * @return Returns true if ok
	 */
	public boolean add(SpSpMatch sp) {
		nr++;
		matchList.add(sp);
		historyScores.add(sp.scoringValue.get(0).score);
		return true;
		// return (super.add(sp) & historyScores.add(sp.scoreValue.score));
	}

	/**
	 * Adds only a score
	 * 
	 * @param d
	 *            the score to add
	 * @return Returns true if ok
	 */
	public boolean add(double d) {
		nr++;
		return historyScores.add(d);
	}

	public void inc() {
		nr++;
	}

	/**
	 * @return the historyScores
	 */
	public ArrayList<Double> getHistoryScores() {
		return historyScores;
	}

	/**
	 * @param historyScores
	 *            the historyScores to set
	 */
	public void setHistoryScores(ArrayList<Double> historyScores) {
		this.historyScores = historyScores;
	}

	/**
	 * @return the nr
	 */
	public int getNr() {
		return nr;
	}

	/**
	 * @param nr
	 *            the nr to set
	 */
	public void setNr(int nr) {
		this.nr = nr;
	}

	public String toString() {
		String s = "TODO";

		// TODO

		return s;
	}

	/**
	 * @return the sorted
	 */
	public Boolean getSorted() {
		return sorted;
	}

	/**
	 * @param sorted
	 *            the sorted to set
	 */
	public void setSorted(Boolean sorted) {
		this.sorted = sorted;
	}

	/**
	 * @return the matchList
	 */
	public SortedSet<SpSpMatch> getMatchList() {
		return matchList;
	}

	/**
	 * @param matchList
	 *            the matchList to set
	 */
	public void setMatchList(SortedSet<SpSpMatch> matchList) {
		this.matchList = matchList;
	}

}
