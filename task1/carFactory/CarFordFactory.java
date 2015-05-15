package tasks.task1.carFactory;

import tasks.task1.Car;
import tasks.task1.CarsFactory;
import tasks.task1.carTypes.CarFord;

public class CarFordFactory extends CarsFactory {

	public Car createCar(int id) {
		return new CarFord(id);
	}
	
}
