package tasks.task1.carTypes;

import tasks.task1.Car;

public class CarAudi extends Car {

	public CarAudi(int id) {
		super(id);		
		name = "Audi";
		price = 1000;
		maxSpeed = 200;
		fuelUsage = 80;
	}
	
}
