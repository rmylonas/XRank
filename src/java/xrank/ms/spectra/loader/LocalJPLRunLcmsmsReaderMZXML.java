package xrank.ms.spectra.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.stax2.XMLStreamReader2;
import org.expasy.jpl.experimental.exceptions.JPLImpossibleToBuildChromatogram;
import org.expasy.jpl.experimental.exceptions.JPLIncompatibleRTUnitException;
import org.expasy.jpl.experimental.exceptions.JPLReaderException;
import org.expasy.jpl.experimental.ms.lcmsms.JPLChromatogramWithRef;
import org.expasy.jpl.experimental.ms.lcmsms.JPLExpSourceDescription;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRetentionTime.RTUnit;
import org.expasy.jpl.experimental.ms.lcmsms.JPLRunLcmsms;
import org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReader;
import org.expasy.jpl.experimental.ms.peak.JPLExpMSPeakLC;
import org.expasy.jpl.experimental.ms.peaklist.JPLFragmentationSpectrum;
import org.expasy.jpl.experimental.ms.peaklist.JPLMS1LCSpectrum;
import org.expasy.jpl.utils.exceptions.JPLEmptyPeaklistException;
import org.expasy.jpl.utils.sort.SimpleTypeArray;
import org.systemsbiology.jrap.MSXMLParser;
import org.systemsbiology.jrap.Scan;

import xrank.ms.spectra.LocalMsPeak;
import xrank.ms.spectra.LocalMsPeakComparator;
import xrank.ms.spectra.advancedInfo.JPLExtraInformationLocalMSImpl;

import com.ctc.wstx.stax.WstxInputFactory;

/**
 * 
 * @author Roman Mylonas
 *
 */

public class LocalJPLRunLcmsmsReaderMZXML extends JPLRunReaderBasic {

	public LocalJPLRunLcmsmsReaderMZXML() { 
		super();
	}

	public LocalJPLRunLcmsmsReaderMZXML(JPLRunReaderBasic runReaderBasic) {
		super(runReaderBasic);
	}

	Log log = LogFactory.getLog(JPLRunLcmsmsReader.class);	

	int currentMsLevel = 0;
	String currentMs1 = null;

	// regexp for parsing 
	Pattern readwPrecursorPattern = Pattern.compile("ms3\\s+([\\d|.]+)");

	Map<String, JPLMS1LCSpectrum> ms1Map = new HashMap<String, JPLMS1LCSpectrum>();
	Map<String, JPLFragmentationSpectrum> msNMap = new HashMap<String, JPLFragmentationSpectrum>();


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.expasy.jpl.experimental.ms.lcmsms.readers.JPLRunLcmsmsReader#buildRun(java.lang.String)
	 */
	@Override
	public JPLRunLcmsms buildRunImpl(String src) throws JPLReaderException {

		String currentPrecursorId = "";

		Map<Integer, Double> precursorMap = new HashMap<Integer, Double>();

		JPLRunLcmsms run = new JPLRunLcmsms();

		JPLExpSourceDescription source = new JPLExpSourceDescription();
		run.setSource(source);
		source.setTitle(src);

		MSXMLParser parser = null;

		try{
			parser = new MSXMLParser(src);
			
			HashMap<String, JPLMS1LCSpectrum> ms1Dico = new HashMap<String, JPLMS1LCSpectrum>();

			boolean emptySpWarning = false;
			boolean peakSkipped = false;
			boolean noPrecursor = false;
			boolean noDirectPrecursor = false;
			
			for (int i=1; i<= parser.getScanCount(); i++) {

				Double directPrecMoz = null;
				String polarity = null;

				// scan current spectrum
				Scan s = parser.rap(i);
				
				int msLevel = s.getMsLevel();

				log.debug("spectrum [" + s.getNum() + "]");
				
				float[][]mzsAndIntensities = s.getMassIntensityList();

				SortedSet<LocalMsPeak> tmpPeaks = new TreeSet<LocalMsPeak>(new LocalMsPeakComparator());
				peakSkipped = convertToSortedSets(tmpPeaks, mzsAndIntensities, peakSkipped);
				
				double masses[]=null;
				double intensities[]=null;
				
				if (tmpPeaks.size() == 0 || s.getPeaksCount() == 0 ) {
					if(!emptySpWarning){
						log.warn("empty spectrum is skipped [" + s.getNum() + "] (following occurences will not be reported..)");
						emptySpWarning = true;
					}
					continue;
				}else{

					int n = tmpPeaks.size();

					if(n>0){
						masses=new double[n];
						intensities=new double[n];

						int j=0;
						for(LocalMsPeak onePeak: tmpPeaks){
							
								masses[j]=onePeak.getMoz();
								intensities[j]=onePeak.getIntensity();
								j++;
						}

					}


				}

				if (msLevel == 1) {

					// set TIC into extra information
					JPLMS1LCSpectrum sp = new JPLMS1LCSpectrum(masses, intensities);
					JPLExtraInformationLocalMSImpl info = new JPLExtraInformationLocalMSImpl();
					JPLExpMSPeakLC peak = new JPLExpMSPeakLC();
					peak.setIntensity(s.getTotIonCurrent());
					info.setPrecurcor(peak);
					sp.setExtraInformation(info);

					run.addMS1Spectrum(sp);

					currentPrecursorId = "" + s.getNum();

					ms1Dico.put(currentPrecursorId, sp);
					ms1Map.put(currentPrecursorId, sp);

					sp.setRetentionTime(mzXMLDuration2retentionTime(s.getRetentionTime()));
					sp.setTitle(currentPrecursorId);


				} else if (msLevel >= 2){

					JPLFragmentationSpectrum sp = 
						new JPLFragmentationSpectrum(masses, intensities);

					sp.setTitle(""+s.getNum());
					msNMap.put(sp.getTitle(), sp);
					sp.setMsLevel(msLevel);
					directPrecMoz = Double.valueOf(s.getPrecursorMz());
					polarity = s.getPolarity();

					//go down to ms1 and get this precursor
					if(s.getMsLevel() > 2){

						//only if index is bigger than 1
						if(i > 1){

							boolean found = true;

							Scan tmpS = parser.rap(i-1);
							int k = 2;
							while(tmpS.getMsLevel() > 2){
								if(i-k <= 0){
									found = false;
									break;
								}				

								tmpS = parser.rap(i-k);
								k++;
							}

							if(tmpS.getMsLevel() == 1 && found){
								s.setPrecursorMz(tmpS.getPrecursorMz());
								s.setPrecursorIntensity(tmpS.getTotIonCurrent());
							}
						}

					}


					double mass = s.getPrecursorMz();

					// for level 2 we store the precursorMass in the hash
					// at for higher levels try to access those values
					// or try to parse the filterLineInfo
					if(msLevel == 2){
						precursorMap.put(s.getNum(), mass);
					}else if(precursorMap.containsKey(s.getPrecursorScanNum())){
						mass = precursorMap.get(s.getPrecursorScanNum());
					}else if(s.getFilterLine() != null){						
						Matcher m = readwPrecursorPattern.matcher(s.getFilterLine());
						if(m.find()){
							mass = Double.valueOf(m.group(1));
						}
					}

					double intensity = s.getTotIonCurrent();

					// bug with QStar data
					if(mass < 0){
						//continue;
						if(!noPrecursor){
							log.warn("no precursor mass found, applying 0.0 for spectrum  ["+ sp.getTitle() + "] (following occurences will not be reported..)");
							noPrecursor = true;
						}
						mass = 0.0;
					}

					log.debug("scan " + i + " precursor intensity : "+intensity);

					JPLExpMSPeakLC precursor = new JPLExpMSPeakLC(mass, intensity);
					precursor.setRetentionTime(mzXMLDuration2retentionTime(s.getRetentionTime()));
					sp.setPrecursor(precursor);

					int chargeState = s.getPrecursorCharge();

					if (chargeState < 0) {
						chargeState = 0;
					}

					// check weither there is a direct precursor
					if(directPrecMoz == null || directPrecMoz < 0){
						if(!noDirectPrecursor){
							log.warn("no direct precursor mass found, applying 0.0 for spectrum  ["+ sp.getTitle() + "] (following occurences will not be reported..)");
							noDirectPrecursor = true;
						}
						directPrecMoz = 0.0;
					}

					precursor.setChargeState(chargeState);

					run.addFragmentationSpectrum(sp);
				}

			}

		}catch(Exception e){

			if("no <indexOffset> found".equals(e.getMessage())){
				log.debug("no <indexOffset> found: treat this run as empty");
				return run;
			}

			throw new JPLReaderException("exception parsing [" + src +"]", e);

		}	

		if(run.getFragmentationSpectraList().size() <=0 ){
			throw new JPLEmptyPeaklistException("no fragmentation spectra found in this run");

			// if there are ms1 spectra, we try to use their TIC information as the precursor-int
		}else if(run.getMs1SpectraList().size() > 0){
			try {
				this.adjustPrecursorIntensity(src);
			} catch (Exception e) {
				throw new JPLReaderException("could not adjust precursor intensities", e);
			}
		}

		return run;
	}


	private boolean convertToSortedSets(SortedSet<LocalMsPeak> tmpPeaks, float[][]mzsAndIntensities, boolean peakSkipped){	

		for(int i=0; i<mzsAndIntensities[0].length; i++){
			double intensity = Double.valueOf(mzsAndIntensities[1][i]);
			double moz = Double.valueOf(mzsAndIntensities[0][i]);
			
			if(intensity < 0){
				intensity = 0.0;
				
				if(!peakSkipped){
					log.warn("peak with intensity <= 0 found: " + mzsAndIntensities[0][i] + " " + 
							mzsAndIntensities[1][i] + " set it to 0.0 (following occurences will not be reported..)");
					peakSkipped = true;
				}
			}	
			
			LocalMsPeak peak = new LocalMsPeak();
			peak.setMoz(moz);
			peak.setIntensity(intensity);
			tmpPeaks.add(peak);
				
		}
		
		return peakSkipped;

	}

	private void adjustPrecursorIntensity(String src) throws Exception{

		File file = null;
		try {
			file = new File(src);
		} catch (Exception e) {
			throw new Exception("file [" + src + "] not found", e);
		}

		try {

			WstxInputFactory factory = new WstxInputFactory();
			XMLStreamReader2 parser = factory.createXMLStreamReader(file);

			while (true) {
				int event = parser.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					parser.close();
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					this.handleStartElement(parser);
				} else if (event == XMLStreamConstants.END_ELEMENT) {
					this.handleEndElement(parser);
				}
			}

		} catch (Exception e) {
			throw new Exception("unable to parse file [" + src + "]", e);
		}

	}


	protected void handleStartElement(XMLStreamReader2 parser) throws Exception {

		if(this.currentMsLevel <= 0){

			if ("scan".equals(parser.getLocalName())) {

				this.currentMs1 = parser.getAttributeValue(null, "num");

				this.currentMsLevel ++;

			} 

		}else if(this.currentMsLevel >= 1){

			if(this.currentMs1 != null){

				if ("scan".equals(parser.getLocalName())) {

					String peaksCount = parser.getAttributeValue(null, "peaksCount");

					this.currentMsLevel ++;

					if(! "0".equals(peaksCount)){

						String currentMsN = parser.getAttributeValue(null, "num");

						JPLExtraInformationLocalMSImpl info = (JPLExtraInformationLocalMSImpl) this.ms1Map.get(this.currentMs1).getExtraInformation();
						double intensity = info.getPrecurcor().getIntensity();
						this.msNMap.get(currentMsN).getPrecursor().setIntensity(intensity);
						
					}
				}

			}

		}
	}



	protected void handleEndElement(XMLStreamReader2 parser) throws Exception {

		if ("scan".equals(parser.getLocalName())) {

			if(this.currentMsLevel > 0){
				this.currentMsLevel--;
			}
			if(this.currentMsLevel == 0){
				this.currentMs1 = null;
			}
		}
	}



	static private JPLRetentionTime mzXMLDuration2retentionTime(String duration){

		if (duration == null) {
			throw new IllegalStateException("undefined retention time.");
		}

		/* XML Duration Data Type

		The duration data type is used to specify a time interval.

		The time interval is specified in the following form "PnYnMnDTnHnMnS" where:

		 * P indicates the period (required)
		 * nY indicates the number of years
		 * nM indicates the number of months
		 * nD indicates the number of days
		 * T indicates the start of a time section (required if you are going to specify hours, minutes, or seconds)
		 * nH indicates the number of hours
		 * nM indicates the number of minutes
		 * nS indicates the number of seconds 

		 */
		/// simplified pattern for mzXML retention time 
		Pattern durationPattern = Pattern.compile("PT([\\d\\.]+)([SM])");

		Matcher matcher = durationPattern.matcher(duration);
		double retentionTime = 0;
		char retentionTimeUnit = 'S';

		if (matcher.matches()) {
			retentionTime = Double.parseDouble(matcher.group(1));
			retentionTimeUnit = matcher.group(2).charAt(0);
		}

		return new JPLRetentionTime(retentionTime,
				(retentionTimeUnit == 'S') ? RTUnit.second : RTUnit.minute);
	}

	@Override
	public void buildChromatogramTIC(JPLRunLcmsms run)
	throws JPLIncompatibleRTUnitException,
	JPLImpossibleToBuildChromatogram {		

	}

	@Override
	public void buildChromatogramXIC(JPLRunLcmsms run)
	throws JPLIncompatibleRTUnitException,
	JPLImpossibleToBuildChromatogram {		

		// JPLChromatogramXICBuilder chromatoBuilder=new JPLChromatogramXICBuilder();
		// JPLChromatogramWithRef<JPLFragmentationSpectrum> chromato=chromatoBuilder.buildChromatogram(run);
		JPLChromatogramWithRef<JPLFragmentationSpectrum> chromato=this.buildXICChromatogram(run);
		run.setChromatogramXIC(chromato);
	}

	private JPLChromatogramWithRef<JPLFragmentationSpectrum> buildXICChromatogram(JPLRunLcmsms run) throws JPLIncompatibleRTUnitException, JPLImpossibleToBuildChromatogram{

		int n=run.getFragmentationSpectraList().size();


		double rt[]=new double[n];
		double intensities[]=new double[n];
		int i=0;
		RTUnit rtunit=null;

		for(JPLFragmentationSpectrum sp:run.getFragmentationSpectraList()){
			if(sp.getRetentionTime()==null){
				throw new JPLImpossibleToBuildChromatogram("undefined retention time for spectrum "+sp.getTitle());
			}
			rt[i]=sp.getRetentionTime().getValue();

			intensities[i]=sp.getPrecursor().getIntensity();
			i++;
			if(rtunit==null){
				rtunit=sp.getPrecursor().getRetentionTime().getUnit();
			}else{
				if(rtunit!=sp.getPrecursor().getRetentionTime().getUnit()){
					throw new JPLIncompatibleRTUnitException(""+sp.getPrecursor().getRetentionTime().getUnit()+" vs previous "+rtunit+" for spectrum "+sp.getTitle());
				}
			}
		}

		SimpleTypeArray.sortUpArrraysOnFirst(new double[][] {rt, intensities});

		JPLChromatogramWithRef<JPLFragmentationSpectrum> chromato = null;

		if(n > 1){
			chromato=new JPLChromatogramWithRef<JPLFragmentationSpectrum>(rt, intensities, rtunit);

			i=0;
			for(JPLFragmentationSpectrum sp:run.getFragmentationSpectraList()){
				chromato.setRef(i++, sp);
			}
		}

		return chromato;
	}


	private JPLChromatogramWithRef<JPLMS1LCSpectrum> buildTICChromatogram(JPLRunLcmsms run) throws JPLIncompatibleRTUnitException, JPLImpossibleToBuildChromatogram{

		int n=run.getFragmentationSpectraList().size();
		double rt[]=new double[n];
		double intensities[]=new double[n];
		int i=0;
		RTUnit rtunit=null;


		if(run.getMs1SpectraList().size() > 0){

			for(JPLMS1LCSpectrum sp1 : run.getMs1SpectraList()){

				//

			}

		}else{

			for(JPLFragmentationSpectrum sp:run.getFragmentationSpectraList()){
				if(sp.getRetentionTime()==null){
					throw new JPLImpossibleToBuildChromatogram("undefined retention time for spectrum "+sp.getTitle());
				}
				rt[i]=sp.getRetentionTime().getValue();
			//	System.out.println(i+ " "+ sp.getRetentionTime().getValue());
				intensities[i]=sp.getPrecursor().getIntensity();
				i++;
				if(rtunit==null){
					rtunit=sp.getRetentionTime().getUnit();
				}else{
					if(rtunit!=sp.getRetentionTime().getUnit()){
						throw new JPLIncompatibleRTUnitException(""+sp.getRetentionTime().getUnit()+" vs previous "+rtunit+" for spectrum "+sp.getTitle());
					}
				}

			}
		}

		SimpleTypeArray.sortUpArrraysOnFirst(new double[][] {rt, intensities});
		JPLChromatogramWithRef<JPLMS1LCSpectrum> chromato=new JPLChromatogramWithRef<JPLMS1LCSpectrum>(rt, intensities, rtunit);
		i=0;
		for(JPLMS1LCSpectrum sp:run.getMs1SpectraList()){
			chromato.setRef(i++, sp);
		}
		return chromato;
	}


}
