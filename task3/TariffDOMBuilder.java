package tasks.task3;

import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TariffDOMBuilder extends AbstractTariffsBuilder {
	
	private DocumentBuilder docBuilder;
	
	public TariffDOMBuilder() {
		this.tariffs = new HashSet<Tariff>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch(ParserConfigurationException e) {
			System.err.println("Error in parser configuration: "+e);
		}
	}
	
	public void buildSetTariffs(String filename) {
		Document document = null;
		try {
			document = docBuilder.parse(filename);
			Element root = document.getDocumentElement();
			NodeList tariffsList = root.getElementsByTagName("tariff");
			for(int i=0; i<tariffsList.getLength(); i++) {
				Element tariffElement = (Element)tariffsList.item(i);
				Tariff tariff = buildTariff(tariffElement);
				tariffs.add(tariff);
			}
		} catch(IOException e) {
			System.err.println("File error or I/O error: "+e);
		} catch(SAXException e) {
			System.err.println("Parsing failure: "+e);
		}
	}
	
	private Tariff buildTariff(Element tariffElement) {
		Tariff tariff = new Tariff();		

		Element nameElement = (Element)tariffElement.getElementsByTagName("name").item(0);
		tariff.setTariffID(nameElement.getAttribute("tariffID"));
		tariff.setName(getElementTextContent(tariffElement, "name"));		
		tariff.setOperatorName(getElementTextContent(tariffElement, "operatorName"));		
		tariff.setPayroll(Integer.parseInt(getElementTextContent(tariffElement, "payroll")));
		Tariff.CallPrices callPrices = tariff.getCallPrices();
		Element callPricesElement = (Element)tariffElement.getElementsByTagName("callPrices").item(0);
		callPrices.setInsideNetwork(Double.parseDouble(getElementTextContent(callPricesElement, "insideNetwork")));
		callPrices.setOutsideNetwork(Double.parseDouble(getElementTextContent(callPricesElement, "outsideNetwork")));
		callPrices.setLandLinePhone(Double.parseDouble(getElementTextContent(callPricesElement, "landLinePhone")));
		tariff.setSmsPrice(Double.parseDouble(getElementTextContent(tariffElement, "smsPrice")));
		Tariff.Parameters parameters = tariff.getParameters();
		Element parametersElement = (Element)tariffElement.getElementsByTagName("parameters").item(0);
		Element favouriteNumberElement = (Element)tariffElement.getElementsByTagName("favouriteNumber").item(0);		
		parameters.setCountFavourNumber(Integer.parseInt(favouriteNumberElement.getAttribute("count")));
		NodeList numberList = tariffElement.getElementsByTagName("number");		
		for(int i=0; i<numberList.getLength(); i++) {			
			Node node = numberList.item(i);			
			parameters.setFavouriteNumber(node.getTextContent());
		}
		parameters.setTariffication(getElementTextContent(parametersElement, "tariffication"));
		parameters.setConnectionCost(Double.parseDouble(getElementTextContent(parametersElement, "connectionCost")));
		return tariff;
	}
	
	private static String getElementTextContent(Element element, String elementName) {
		NodeList nodeList = element.getElementsByTagName(elementName);
		Node node = nodeList.item(0);
		String text = node.getTextContent();
		return text;
	}
	
}
