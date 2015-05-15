package tasks.task1;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Random;

public class AutoPark {

	private CarsFactory[] carsFactory = null;
	private Car[] cars = null;
	private int numberCars;
	
	public AutoPark(CarsFactory[] carsFactory, int numberCars) {
		
		this.carsFactory = carsFactory;
		this.numberCars = numberCars;
		this.cars = new Car[this.numberCars]; 
		fillAutoParkWithCars();
	}
	
	private void fillAutoParkWithCars() {
		
		int currentIndex = 0;
		int avarageNumberCarsByType = this.numberCars/carsFactory.length;
		while (currentIndex < this.numberCars-1) {
			for (CarsFactory factory : carsFactory) {
				int numberNewCars = new Random().nextInt(avarageNumberCarsByType);
				if ( currentIndex + numberNewCars > this.numberCars-1)
					numberNewCars = this.numberCars - 1 - currentIndex;
				for (int i=currentIndex; i < currentIndex + numberNewCars + ((currentIndex == 0)?0:1); i++)
					cars[i] = factory.createCar(i);
				currentIndex += numberNewCars;
			}
		}		
	}
	
	public int getPriceOfAutoPark() {
		 
		int totalPrice = 0;
		for (Car car : cars)
			totalPrice += car.getPrice();
		return totalPrice;
	}
	
	public List<Car> getCarsBySpeedRange(int lowSpeed, int highSpeed) {
		
		if ( lowSpeed < 0 || highSpeed < 0 )
			throw new IllegalArgumentException("in method getCarsBySpeedRange lowSpeed or highSpeed < 0");
		
		List<Car> foundCars = new LinkedList<>();
		
		for (Car car : cars) {
			int currentCarSpeed = car.getMaxSpeed();
			if ( currentCarSpeed >= lowSpeed && currentCarSpeed <= highSpeed )
				foundCars.add(car);			
		}
		
		return foundCars;		
	}
	
	public String carsToString(Car[] carsToShow) {
		
		if ( carsToShow == null )
			throw new IllegalArgumentException("in method carsToString argument carsToShow is null");
		
		String resultString = "";
		for (Car car : carsToShow)
			resultString += car.getName() + "-" + car.getId() + " ( Price=" + car.getPrice() + "; Speed=" + car.getMaxSpeed() + "; FuelUsage=" + car.getFuelUsage() + " )\n";

		return resultString;
	}	
	
	public String toString() {
		
		return carsToString(cars);		
	}
	
	public Car[] sortCarsByFuelUsage() {
		
		Car[] sortedCars = Arrays.copyOf(cars, cars.length);		
		FuelUsageComparator comparator = new FuelUsageComparator();
		sortAlgorithm(sortedCars, 0, sortedCars.length-1, comparator);		
		return sortedCars;
	}
	
	class FuelUsageComparator implements Comparator<Car> {
		
		public int compare(Car car1, Car car2) {
			return car1.getFuelUsage() - car2.getFuelUsage();
		}
	}
	
	private void sortAlgorithm(Car[] inputCars, int left, int right, Comparator<Car> comparator) {
		
		if (inputCars == null)
			throw new IllegalArgumentException("List is empty");

		if (left<0||right<0||left>=inputCars.length||right>=inputCars.length)
			throw new IllegalArgumentException("Index right or left is out of bounds !!!");
		
		int leftIndex = left;
		int rightIndex = right;	    
	    int medIndex = (leftIndex+rightIndex)/2;
	    Car medValue = inputCars[medIndex];

	    while (leftIndex <= rightIndex) {
	      while ( comparator.compare(inputCars[leftIndex], medValue) > 0)
	    	  leftIndex++;
	      while ( comparator.compare(inputCars[rightIndex], medValue) < 0)
	    	  rightIndex--;
	      if (leftIndex <= rightIndex) {
	    	  Car temp = inputCars[leftIndex];
	    	  inputCars[leftIndex] = inputCars[rightIndex];
	    	  inputCars[rightIndex] = temp;	    	  
	    	  leftIndex++;
	    	  rightIndex--;
	      }
	    }
	    if (left < rightIndex)
	    	sortAlgorithm(inputCars, left, rightIndex, comparator);
	    if (leftIndex < right)
	    	sortAlgorithm(inputCars, leftIndex, right, comparator);	    
	}
	
}
