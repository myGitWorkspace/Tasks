package tasks.task3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import tasks.task3.TariffHandler.TariffEnum;

public class TariffStAXBuilder extends AbstractTariffsBuilder {
	
	private HashSet<Tariff> tariffs = new HashSet<>();
	private XMLInputFactory inputFactory;	
	
	public TariffStAXBuilder() {
		inputFactory = XMLInputFactory.newInstance();
		super.tariffs = this.tariffs;
	}
	
	public void buildSetTariffs(String filename) {
		FileInputStream inputStream = null;
		XMLStreamReader reader = null;
		String name;
		try {
			inputStream = new FileInputStream(new File(filename));
			reader = inputFactory.createXMLStreamReader(inputStream);
			while(reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.TARIFF) {
						Tariff tariff = buildTariff(reader);
						tariffs.add(tariff);
					}
				}
			}
		} catch(XMLStreamException e) {
			System.err.println("StAX parsing error! " + e.getMessage());
		} catch(FileNotFoundException e) {
			System.err.println("File " + filename+" not found! " + e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch(IOException e) {
				System.err.println("Impossible close file " + filename + " : " + e);
			}
		}
		
	}
	
	private Tariff buildTariff(XMLStreamReader reader) throws XMLStreamException {
		Tariff tariff = new Tariff();
		String name;
		while(reader.hasNext()) {
			int type = reader.next();
			switch(type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				switch(TariffEnum.valueOf(name.toUpperCase())) {
				case NAME:
					String tariffID = reader.getAttributeValue(null, TariffEnum.TARIFFID.getValue());
					tariff.setTariffID(tariffID);
					tariff.setName(getXMLText(reader));
					break;				
				case OPERATORNAME:
					tariff.setOperatorName(getXMLText(reader));
					break;
				case PAYROLL:
					tariff.setPayroll(Integer.parseInt(getXMLText(reader)));
					break;					
				case CALLPRICES:
					tariff.setCallPrices(getXMLCallPrices(reader));
					break;					
				case SMSPRICE:
					tariff.setSmsPrice(Double.parseDouble(getXMLText(reader)));
					break;					
				case PARAMETERS:
					tariff.setParameters(getXMLParameters(reader));
					break;					
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if(TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.TARIFF)
					return tariff;
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag Tariff");
	}
	
	private Tariff.CallPrices getXMLCallPrices(XMLStreamReader reader) throws XMLStreamException {
		Tariff.CallPrices callPrices = new Tariff.CallPrices();
		int type;
		String name;
		while(reader.hasNext()) {
			type = reader.next();
			switch(type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				switch(TariffEnum.valueOf(name.toUpperCase())) {
				case INSIDENETWORK:
					callPrices.setInsideNetwork(Double.parseDouble(getXMLText(reader)));
					break;
				case OUTSIDENETWORK:				
					callPrices.setOutsideNetwork(Double.parseDouble(getXMLText(reader)));
					break;
				case LANDLINEPHONE:				
					callPrices.setLandLinePhone(Double.parseDouble(getXMLText(reader)));
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if( TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.CALLPRICES) {
					return callPrices;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag callPrices");
	}
	
	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if(reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}

	private Tariff.Parameters getXMLParameters(XMLStreamReader reader) throws XMLStreamException {
		Tariff.Parameters parameters = new Tariff.Parameters();
		int type;
		String name;
		while(reader.hasNext()) {
			type = reader.next();
			switch(type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();				
				switch(TariffEnum.valueOf(name.toUpperCase())) {
				case FAVOURITENUMBER:
					String count = reader.getAttributeValue(null, TariffEnum.COUNT.getValue()); 
					parameters.setCountFavourNumber(Integer.parseInt(count));
					break;
				case NUMBER:
					parameters.setFavouriteNumber(getXMLText(reader));
					break;
				case TARIFFICATION:				
					parameters.setTariffication(getXMLText(reader));
					break;
				case CONNECTIONCOST:				
					parameters.setConnectionCost(Double.parseDouble(getXMLText(reader)));
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if( TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.PARAMETERS) {
					return parameters;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag Parameters");
	}
	
	
	
}
