package tasks.task1.carTypes;

import tasks.task1.Car;

public class CarToyota extends Car {

	public CarToyota(int id) {		
		super(id);
		name = "Toyota";
		price = 4000;
		maxSpeed = 350;
		fuelUsage = 150;
	}
	
}
