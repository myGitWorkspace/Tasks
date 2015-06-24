package src.java.model;

import java.util.List;

public class Customer extends User {
	
	private List<TechnicalTask> technicalTasks;
	
	public Customer(int id, String login, String password, String name, String lastName) {
		super(id, login, password, name, lastName);
	}
}
