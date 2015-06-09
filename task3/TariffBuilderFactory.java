package tasks.task3;

public class TariffBuilderFactory {
	
	private enum TypeParser {
		SAX, STAX, DOM
	}
	
	public AbstractTariffsBuilder createTariffBuilder(String typeParser) {
		
		TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
		
		switch (type) {
		case DOM:
			return new TariffDOMBuilder();
		case STAX:
			return new TariffStAXBuilder();
		case SAX:
			return new TariffSAXBuilder();
		default:
			throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
		}
	}
		
		
		
}
