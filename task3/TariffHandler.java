package tasks.task3;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class TariffHandler extends DefaultHandler {

	private Set<Tariff> tariffs;
	private Tariff currentTariff = null;
	private TariffEnum currentEnum = null;
	private EnumSet<TariffEnum> parsedFileTitles;
	
	public enum TariffEnum {
		TARIFFS("tariffs"),
		TARIFF("tariff"),
		NAME("name"),
		TARIFFID("tariffID"),
		OPERATORNAME("operatorName"),
		PAYROLL("payroll"),
		CALLPRICES("callPrices"),
		INSIDENETWORK("insideNetwork"),
		OUTSIDENETWORK("outsideNetwork"),
		LANDLINEPHONE("landLinePhone"),
		SMSPRICE("smsPrice"),
		PARAMETERS("parameters"),
		COUNT("count"),
		FAVOURITENUMBER("favouriteNumber"),
		NUMBER("number"),
		TARIFFICATION("tariffication"),
		CONNECTIONCOST("connectionCost");
		
		private String value;
		private TariffEnum(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public TariffHandler() {
		tariffs = new HashSet<Tariff>();
		parsedFileTitles = EnumSet.range(TariffEnum.TARIFFS, TariffEnum.CONNECTIONCOST);
	}
	
	public Set<Tariff> getTariff() {
		return tariffs;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if ("tariff".equals(localName)) {
			currentTariff = new Tariff();			
		} else if ("name".equals(localName)) {
			currentTariff.setTariffID(attributes.getValue(0));
		} else if ("favouriteNumber".equals(localName)) {
			currentTariff.getParameters().setCountFavourNumber(Integer.parseInt(attributes.getValue(0)));
		}
		
		TariffEnum tempEnum = TariffEnum.valueOf(localName.toUpperCase());
		if (parsedFileTitles.contains(tempEnum))
			currentEnum = tempEnum;
	}
	
	public void endElement(String uri, String localName, String qName) {
		if ("tariff".equals(localName)) {
			tariffs.add(currentTariff);			
		}			
	}
	
	public void characters(char[] chars, int start, int length) {
		String currentString = new String(chars, start, length).trim();
		if (currentEnum != null) {
			switch(currentEnum) {
			case TARIFFS:
				break;
			case TARIFF:
				break;
			case FAVOURITENUMBER:
				break;
			case NAME:				
				currentTariff.setName(currentString);
				break;				
			case OPERATORNAME:
				currentTariff.setOperatorName(currentString);
				break;
			case PAYROLL:
				currentTariff.setPayroll(Integer.parseInt(currentString));
				break;
			case CALLPRICES:
				break;
			case INSIDENETWORK:
				currentTariff.getCallPrices().setInsideNetwork(Double.parseDouble(currentString));
				break;
			case OUTSIDENETWORK:				
				currentTariff.getCallPrices().setOutsideNetwork(Double.parseDouble(currentString));
				break;
			case LANDLINEPHONE:				
				currentTariff.getCallPrices().setLandLinePhone(Double.parseDouble(currentString));
				break;
			case SMSPRICE:
				currentTariff.setSmsPrice(Double.parseDouble(currentString));
				break;
			case PARAMETERS:
				break;
			case NUMBER:
				currentTariff.getParameters().setFavouriteNumber(currentString);
				break;
			case TARIFFICATION:
				currentTariff.getParameters().setTariffication(currentString);
				break;
			case CONNECTIONCOST:
				currentTariff.getParameters().setConnectionCost(Double.parseDouble(currentString));
				break;
			default:
				throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
			}
		}
		currentEnum = null;
	}
	
}
