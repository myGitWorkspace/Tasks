package src.java.model;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

enum TechnicalTaskState {
	WAIT, ACCEPTED, DENYED
}

public class TechnicalTask {

	private int id;
	private String title;
	private String description;
	private List<Task> tasks = new ArrayList<>();
	private TechnicalTaskState technicalTaskState;
	private Calendar deadline;
	private Calendar creationDate;
	private Map<Calendar,Integer> workHoursDaily = new HashMap<>();
	private String customerName;
	
	public TechnicalTask() {
	}
	
	public TechnicalTask( int id, String title, String description, List<Task> tasksList) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		tasks = new LinkedList<Task>();
		if (tasksList != null)
			this.tasks.addAll(tasksList);
		technicalTaskState = TechnicalTaskState.WAIT;
	}

	public void accept() {
		technicalTaskState = TechnicalTaskState.ACCEPTED;
	}
	
	public void deny() {
		technicalTaskState = TechnicalTaskState.DENYED;
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTasks(List<Task> tasksList) {
		this.tasks.addAll(tasksList);
	}
	
	public void setTechnicalTaskState(String technicalTaskState) {
		if (technicalTaskState != null) {
			TechnicalTaskState state = TechnicalTaskState.valueOf(technicalTaskState.toUpperCase());
			this.technicalTaskState = state;			
		}
	}
	
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}
	
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setWorkHoursDaily(Map<Calendar,Integer> workHoursDaily) {
		this.workHoursDaily.putAll(workHoursDaily);
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public TechnicalTaskState getTechnicalTaskState() {
		return technicalTaskState;
	}
	
	public Calendar getDeadline() {
		return deadline;
	}
	
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	public Map<Calendar,Integer> getWorkHoursDaily() {
		return workHoursDaily;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
}
