/**
 * Java Proteomics Library https://sourceforge.net/projects/javaprotlib/
 * copyright 2008 PIG(SIB)/GeneBio
 * http://www.isb-sib.ch
 * http://www.genebio.com
 */
package org.expasy.jpl.experimental.ms.lcmsms.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expasy.jpl.experimental.exceptions.JPLImpossibleToBuildChromatogram;
import org.expasy.jpl.experimental.exceptions.JPLIncompatibleRTUnitException;
import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLExpSourceDescription;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime.RTUnit;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.utils.iterator.JPLIterable.JPLIterator;

/**
 * @author Roman Mylonas
 *
 */
public class JPLRunLcmsmsReaderMGFLocal extends JPLRunLcmsmsReader {

	Log log;

	public JPLRunLcmsmsReaderMGFLocal() {
		log = LogFactory.getLog(this.getClass());
	}

	@Override
	public JPLRunLcmsms buildRunImpl(String src) throws JPLReaderException {
		JPLRunLcmsms run = new JPLRunLcmsms();

		JPLExpSourceDescription source=new JPLExpSourceDescription();
		run.setSource(source);
		source.setTitle(src);
		source.setFilename(src);

		try{
			BufferedReader reader =new BufferedReader(new FileReader(src));
			Pattern patTitle=Pattern.compile("###\\s*(\\S+.*)");
			Pattern patRT=Pattern.compile("([\\d\\.]+)\\s*([min|s])");
			String line;
			while((line=reader.readLine())!=null){
				Matcher match=patTitle.matcher(line);
				if(match.find()){
					source.setTitle(match.group(1));
					log.debug("TITLE="+source.getTitle());
					break;
				}
			}
			reader.close();
			
			
			JPLSpectrumParserMGF parser=new JPLSpectrumParserMGF(new FileInputStream(src));
			JPLIterator<JPLFragmentationSpectrum> it=parser.iterator();
			JPLFragmentationSpectrum sp;
			while((sp=it.next())!=null){
				run.addFragmentationSpectrum(sp);
				Matcher match=patRT.matcher(sp.getTitle());
				if(match.find()){
				
				if("m".equals(match.group(2)))
					sp.getPrecursor().setRetentionTime(new JPLRetentionTime(Double.parseDouble(match.group(1))*60, RTUnit.second));				
				
				if("s".equals(match.group(2)))
					sp.getPrecursor().setRetentionTime(new JPLRetentionTime(Double.parseDouble(match.group(1)), RTUnit.second));				
				}else{
					log.debug("unable to parse retention time from "+sp.getTitle());
				}
			}
		}catch(Exception e){
			throw new JPLReaderException(e);
		}

		return run;
	}

	@Override
	/**
	 * nothing t be done as there is no MS1 info
	 */
	public void buildChromatogramTIC(JPLRunLcmsms run)
	throws JPLIncompatibleRTUnitException,
	JPLImpossibleToBuildChromatogram {

	}

	@Override
	public void buildChromatogramXIC(JPLRunLcmsms run)
	throws JPLIncompatibleRTUnitException,
	JPLImpossibleToBuildChromatogram {

}
	
}
