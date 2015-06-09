package tasks.task3;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class ValidatorSAXXSD {

	private String fileName;
	private String schemaName;
	
	public ValidatorSAXXSD(String fileName, String schemaName) {
		this.fileName = fileName;
		this.schemaName = schemaName;
	}
	
	public boolean validate() {
		boolean success = false;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		File schemaLocation = new File(schemaName);
		try {
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(fileName);
			validator.validate(source);
			success = true;
		} catch(SAXException e) {
			System.err.print("validation " + fileName + " is not valid because " + e.getMessage());
		} catch(IOException e) {
			System.err.print(fileName + " is not valid because " + e.getMessage());
		}
		return success;
	}
}
