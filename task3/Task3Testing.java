package tasks.task3;

import org.junit.Test;
import org.junit.Assert;
import java.util.List;

public class Task3Testing {

	public static void main(String[] args) {
		
		String xmlFilename = "src\\tasks\\task3\\xml\\tariffs.xml";
		String xmlValidationFilename = "src\\tasks\\task3\\xml\\tariffsValidation.xml";
		String schemaFilename = "src\\tasks\\task3\\xml\\tariffs.xsd";
		String xslFilename = "src\\tasks\\task3\\xml\\tariffs.xsl";
		String newHTMLfilename = "src\\tasks\\task3\\xml\\tariffs.html";
		
		ValidatorSAXXSD validator = new ValidatorSAXXSD(xmlValidationFilename, schemaFilename);
		System.out.println( "Validation of xml file " + xmlValidationFilename + " is " + validator.validate() );
				
		TariffBuilderFactory tariffFactory = new TariffBuilderFactory();
		AbstractTariffsBuilder builder = tariffFactory.createTariffBuilder("sax");
		builder.buildSetTariffs(xmlFilename);
		System.out.println(builder.getSortedTariffs());
		
		tariffFactory = new TariffBuilderFactory();
		builder = tariffFactory.createTariffBuilder("dom");
		builder.buildSetTariffs(xmlFilename);
		System.out.println(builder.getSortedTariffs());
		
		tariffFactory = new TariffBuilderFactory();
		builder = tariffFactory.createTariffBuilder("stax");
		builder.buildSetTariffs(xmlFilename);
		System.out.println(builder.getSortedTariffs());		
		
		XSLTransformation transformer = new XSLTransformation(xslFilename, xmlFilename, newHTMLfilename);
		System.out.println(transformer.createHTMLfromXML());
		
		xmlValidatorTest(validator);
		saxParserTest(xmlFilename);
		domParserTest(xmlFilename);
		staxParserTest(xmlFilename);
		xslTransformationTest(xslFilename, xmlFilename, newHTMLfilename);
	}
	
	@Test
	public static void xmlValidatorTest(ValidatorSAXXSD validator) {
		Assert.assertEquals(true, validator.validate());
	}
	
	@Test
	public static void saxParserTest(String xmlFilename) {
		TariffBuilderFactory tariffFactory = new TariffBuilderFactory();
		AbstractTariffsBuilder builder = tariffFactory.createTariffBuilder("sax");
		builder.buildSetTariffs(xmlFilename);
		List<Tariff> tariffs = builder.getSortedTariffs();
		Tariff tariff = tariffs.get(0);
		Assert.assertEquals("Dlya dzvinkiv", tariff.getName());
		Assert.assertEquals("asdf1234", tariff.getTariffID());
		Assert.assertEquals("Kievstar", tariff.getOperatorName());
		Assert.assertEquals(52, tariff.getPayroll());
		Assert.assertEquals(4.5, tariff.getCallPrices().getInsideNetwork(),0.001);
		Assert.assertEquals(4.6, tariff.getCallPrices().getOutsideNetwork(),0.001);
		Assert.assertEquals(4.7, tariff.getCallPrices().getLandLinePhone(),0.001);
		Assert.assertEquals(1.3, tariff.getSmsPrice(),0.001);
		Assert.assertEquals("12345634", tariff.getParameters().getFavouriteNumber()[0]);
		Assert.assertEquals("123456344", tariff.getParameters().getFavouriteNumber()[1]);
		Assert.assertEquals(2, tariff.getParameters().getCountFavourNumber());
		Assert.assertEquals(0.24, tariff.getParameters().getConnectionCost(),0.001);
		Assert.assertEquals(Tariff.Tariffication.MINUTE1, tariff.getParameters().getTariffication());
	}
	
	@Test
	public static void domParserTest(String xmlFilename) {
		TariffBuilderFactory tariffFactory = new TariffBuilderFactory();
		AbstractTariffsBuilder builder = tariffFactory.createTariffBuilder("dom");
		builder.buildSetTariffs(xmlFilename);
		List<Tariff> tariffs = builder.getSortedTariffs();
		Tariff tariff = tariffs.get(1);
		Assert.assertEquals("Dlya smartfona extra", tariff.getName());
		Assert.assertEquals("asdf1235", tariff.getTariffID());
		Assert.assertEquals("Kievstar", tariff.getOperatorName());
		Assert.assertEquals(51, tariff.getPayroll());
		Assert.assertEquals(4.2, tariff.getCallPrices().getInsideNetwork(),0.001);
		Assert.assertEquals(4.3, tariff.getCallPrices().getOutsideNetwork(),0.001);
		Assert.assertEquals(4.4, tariff.getCallPrices().getLandLinePhone(),0.001);
		Assert.assertEquals(1.2, tariff.getSmsPrice(),0.001);
		Assert.assertEquals("12345634", tariff.getParameters().getFavouriteNumber()[0]);		
		Assert.assertEquals(1, tariff.getParameters().getCountFavourNumber());
		Assert.assertEquals(0.23, tariff.getParameters().getConnectionCost(),0.001);
		Assert.assertEquals(Tariff.Tariffication.SECONDS12, tariff.getParameters().getTariffication());
	}
	
	@Test
	public static void staxParserTest(String xmlFilename) {
		TariffBuilderFactory tariffFactory = new TariffBuilderFactory();
		AbstractTariffsBuilder builder = tariffFactory.createTariffBuilder("dom");
		builder.buildSetTariffs(xmlFilename);
		List<Tariff> tariffs = builder.getSortedTariffs();
		Tariff tariff = tariffs.get(2);
		Assert.assertEquals("Dlya smartfona+", tariff.getName());
		Assert.assertEquals("asdf1236", tariff.getTariffID());
		Assert.assertEquals("Kievstar", tariff.getOperatorName());
		Assert.assertEquals(50, tariff.getPayroll());
		Assert.assertEquals(3.9, tariff.getCallPrices().getInsideNetwork(),0.001);
		Assert.assertEquals(4.0, tariff.getCallPrices().getOutsideNetwork(),0.001);
		Assert.assertEquals(4.1, tariff.getCallPrices().getLandLinePhone(),0.001);
		Assert.assertEquals(1.1, tariff.getSmsPrice(),0.001);
		Assert.assertEquals("12345634", tariff.getParameters().getFavouriteNumber()[0]);
		Assert.assertEquals("123456344", tariff.getParameters().getFavouriteNumber()[1]);
		Assert.assertEquals("1234563445", tariff.getParameters().getFavouriteNumber()[2]);
		Assert.assertEquals("12345634451", tariff.getParameters().getFavouriteNumber()[3]);
		Assert.assertEquals("123456344512", tariff.getParameters().getFavouriteNumber()[4]);
		Assert.assertEquals(5, tariff.getParameters().getCountFavourNumber());
		Assert.assertEquals(0.22, tariff.getParameters().getConnectionCost(),0.001);
		Assert.assertEquals(Tariff.Tariffication.MINUTE1, tariff.getParameters().getTariffication());
	}
	
	@Test
	public static void xslTransformationTest(String xslFilename, String xmlFilename, String newHTMLfilename) {
		XSLTransformation transformer = new XSLTransformation(xslFilename, xmlFilename, newHTMLfilename);
		Assert.assertEquals(true, transformer.createHTMLfromXML());		
	}
	
}
