package xrank.ms.spectra;

import java.util.Comparator;

public class LocalMsPeakComparator implements Comparator<LocalMsPeak> {

	public int compare(LocalMsPeak p1, LocalMsPeak p2) {
		
		if(p1.getMoz() < p2.getMoz()){
			return -1;
		}else{
			return 1;
		}
	
	}

}
