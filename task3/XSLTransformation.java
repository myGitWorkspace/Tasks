package tasks.task3;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTransformation {

	private String xslFilename;
	private String sourceFilename;
	private String resultFilename;
	
	public XSLTransformation(String xslFilename, String sourceFilename, String resultFilename) {
		this.xslFilename = xslFilename;
		this.sourceFilename = sourceFilename;
		this.resultFilename = resultFilename;
	}
	
	public boolean createHTMLfromXML() {
		boolean success = false;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFilename));
			transformer.transform(new StreamSource(sourceFilename), new StreamResult(resultFilename));
			success = true;
		} catch(TransformerException e) {
			System.err.println("Impossible to transform file " + sourceFilename + ":" + e);
		}
		return success;
	}
}
