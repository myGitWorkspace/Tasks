package src.java.model;

enum BillState {
	UNPAYED, PAYED
}

public class Bill {

	private Project project;
	private double price;
	private BillState billState;
	
	public Bill(Project project) {
		this.project = project;
		this.price = countPrice();
		billState = BillState.UNPAYED;
	}
	
	private double countPrice() {
		
		double totalPrice = 0.;
		for(Programmer programmer : project.getProgrammers()) {
			totalPrice += Technology.valueOf(programmer.getTechnology().toUpperCase()).getPrice()*SkillLevel.valueOf(programmer.getSkillLevel().toUpperCase()).getMultiplier();
		}
		return totalPrice;
	}
	
	public void payTheBill() {
		billState = BillState.PAYED;
	}
	
	public boolean hasBillPayed() {
		if (billState == BillState.PAYED)
			return true;
		return false;
	}
	
}
