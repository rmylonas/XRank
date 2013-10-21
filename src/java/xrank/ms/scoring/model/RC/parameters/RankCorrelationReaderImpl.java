/**
 *
 */
package xrank.ms.scoring.model.RC.parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author Roman Mylonas
 * 
 */
public class RankCorrelationReaderImpl implements RankCorrelationReader {

	@SuppressWarnings("unchecked")
	public RCParams readXmlConfig(String filename){
		SAXReader reader = new SAXReader();
		Document document = null;
		
        try {
			document = reader.read(new File(filename));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		RCParams params = new RCParams();	
		
		ArrayList<Node> nodes = (ArrayList<Node>) document.selectNodes("/MsSpectraMatchScoring/oneMsSpectraMatchScoring/param");

		for (Node oneNode : nodes) {

			String paramName = oneNode.valueOf("@name");

			if (paramName.equals("matchTolerance")) {
				params.setMatchTolerance(Double.valueOf(oneNode.getText()));
			} else if (paramName.equals("nrOfPeaks")) {
				params.setNrOfPeaks(Integer.valueOf(oneNode.getText()));
			} else if (paramName.equals("nrOfRanks")) {
				params.setNrOfRanks(Integer.valueOf(oneNode.getText()));
			} else if (paramName.equals("probabilities")) {
				params.setProbabilities(this.stringToMatrix(oneNode.getText()));
				params.setProbabilitiesString(oneNode.getText());
			}
		}

		return params;
	}

	protected List<List<Double>> stringToMatrix(String s) {
		List<List<Double>> matrix = new ArrayList<List<Double>>();
		Pattern pattern = Pattern.compile("^[\\d|\\-]+.*");

//		int i = 0;
		for (String line : s.split("\n")) {

			/* skip the empty lines */
			if (!pattern.matcher(line).matches()) {
				continue;
			}

			ArrayList<Double> list = new ArrayList<Double>();

			for (String val : line.split("\t")) {
				list.add(Double.valueOf(val));
				// matrix.get(i).add(Double.valueOf(val));
			}
			matrix.add(list);

//			i++;
		}

		return matrix;
	}

	protected ArrayList<Double> stringToArrayList(String s) {
		ArrayList<Double> list = new ArrayList<Double>();
		Pattern pattern = Pattern.compile("^\\d+.*");

//		int i = 0;
		for (String line : s.split("\n")) {

			/* skip the empty lines */
			if (!pattern.matcher(line).matches()) {
				continue;
			}

			for (String val : line.split("\t")) {
				list.add(Double.valueOf(val));
			}

//			i++;
		}

		return list;
	}


}
