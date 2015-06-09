package tasks.task3;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TariffSAXBuilder extends AbstractTariffsBuilder {

	private TariffHandler tariffHandler;
	private XMLReader reader;
	
	public TariffSAXBuilder() {
		tariffHandler = new TariffHandler();
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(tariffHandler);
		} catch(SAXException e) {
			System.err.print("error in SAX parser: " + e);
		}
	}
	
	public void buildSetTariffs(String filename) {
		try {
			reader.parse(filename);
		} catch(SAXException e) {
			System.err.print("error in SAX parser: " + e);
		} catch(IOException e) {
			System.err.print("error in I/O streaming: " + e);
		}
		tariffs = tariffHandler.getTariff();
	}
	
}
