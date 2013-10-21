/**
 * 
 */
package xrank.ms.spectra;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author roman
 * 
 */
public class ReferenceComparator implements Comparator<Reference>, Serializable {
	public ReferenceComparator() {
	}

	@SuppressWarnings("unused")
	public boolean equals(Double[] arg0) {
		return true;
	}

	public int compare(Reference o1, Reference o2) {
		return o1.getRefValue().compareTo(o2.getRefValue());
	};

}
