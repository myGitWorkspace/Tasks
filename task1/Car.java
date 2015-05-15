package tasks.task1;

public abstract class Car {
	
	protected int id;
	protected String name;
	protected int price;
	protected int maxSpeed;	
	protected int fuelUsage;
	
	public Car(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}	
	
	public String getName() {
		return name;
	}	
	
	public int getPrice() {
		return price;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getFuelUsage() {
		return fuelUsage;
	}	
	
}
