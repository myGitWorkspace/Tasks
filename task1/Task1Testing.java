package tasks.task1;

import tasks.task1.carFactory.CarAudiFactory;
import tasks.task1.carFactory.CarFordFactory;
import tasks.task1.carFactory.CarMazdaFactory;
import tasks.task1.carFactory.CarToyotaFactory;

public class Task1Testing {
	
	public static void main(String[] args) {
		
		CarAudiFactory carAudiFactory = new CarAudiFactory();
		CarFordFactory carFordFactory = new CarFordFactory();
		CarMazdaFactory carMazdaFactory = new CarMazdaFactory();
		CarToyotaFactory carToyotaFactory = new CarToyotaFactory();
		CarsFactory[] carsFactory = new CarsFactory[]{carAudiFactory, carFordFactory, carMazdaFactory, carToyotaFactory};
		AutoPark autoPark = new AutoPark( carsFactory, 24);
		
		System.out.println(" ========= All cars in autoPark ===========");
		System.out.println(autoPark.toString());
		System.out.println("Price of autoPark = " + autoPark.getPriceOfAutoPark() + "\n");		
		System.out.println(" ========= Cars with max speed > 210 and speed < 260 : ===========");
		System.out.println(autoPark.carsToString(autoPark.getCarsBySpeedRange(210, 260).toArray(new Car[0])));
		System.out.println(" ========= All cars sorted by fuel usage ===========");
		System.out.println(autoPark.carsToString(autoPark.sortCarsByFuelUsage()));		
	}
	
}
