package src.java.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

enum ProjectState {
	WAIT_FOR_PAYMENT, STARTED, FINISHED
}

public class Project {
	
	private int id;
	private String title;
	private String description;
	private List<Programmer> programmers = new ArrayList<>();
	private TechnicalTask technicalTask;
	private ITCompany itCompany;
	private Calendar deadline;
	private ProjectState projectState = ProjectState.WAIT_FOR_PAYMENT;
	private Calendar creationDate;
	private int deadlineMonthsNumber;
	
	public Project() {
	}
	
	public Project( TechnicalTask technicalTask, ITCompany itCompany) throws NotEnoughProgrammersForProjectException {		
		this.technicalTask = technicalTask;
		this.itCompany = itCompany;
		try {
			createProject();
		} catch(NotEnoughProgrammersForProjectException e) {
			throw e;
		}		
		deadline = Project.countDeadline(this.deadlineMonthsNumber);
		projectState = ProjectState.WAIT_FOR_PAYMENT;
	}
	
	private void createProject() throws NotEnoughProgrammersForProjectException {
		this.title = technicalTask.getTitle();
		for(Task task : technicalTask.getTasks()) {
			List<Programmer> workers = new ArrayList<>();
			workers = this.itCompany.findFreeProgrammers(task.getTechnology(), task.getSkillLevel());
			if (workers != null && (workers.size() >= task.getProgrammersNumber()) ) {
				for(int i=0; i<task.getProgrammersNumber(); i++) {
					Programmer worker = workers.get(i);
					try {
						worker.setProgrammerForProject(this);
					} catch(SettingProgrammerForProjectException e) {
						throw new NotEnoughProgrammersForProjectException("Some required workers are busy for another tasks");
					}					
					programmers.add(worker);
				}				
			} else
				throw new NotEnoughProgrammersForProjectException("No more programmers with qualification="+ task.getTechnology() + " and skillLevel=" + task.getSkillLevel());
		}
	}
	
	public void start() {
		projectState = ProjectState.WAIT_FOR_PAYMENT;
	}
	
	public void finish() {
		projectState = ProjectState.FINISHED;
	}
	
	public static Calendar countDeadline(int monthsNumber) {
		
		int yearsNumber = monthsNumber/12;
		int monthsPlus = monthsNumber - 12*yearsNumber;
		
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		int month = 0;
		if (currentMonth+monthsPlus > 12) {
			month = currentMonth + monthsPlus - 12;
		} else {
			month = currentMonth + monthsPlus;
		}				
		int year = calendar.get(Calendar.YEAR)+yearsNumber;		
		int date = calendar.get(Calendar.DATE);
	    calendar.clear();
	    calendar.set(year,month,date);
	    
	    return calendar;
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
	
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}
	
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setProjectState(String projectStateStr) {
		if (projectStateStr != null) {
			ProjectState state = ProjectState.valueOf(projectStateStr.toUpperCase());
			this.projectState = state;			
		}
	}
	
	public void setProgrammers(List<Programmer> list) {
		this.programmers.addAll(list);
	}
	
	public void setTechnicalTask(TechnicalTask technicalTask) {
		this.technicalTask = technicalTask;
	}
	
	public void setDeadlineMonthsNumber(int months) {
		this.deadlineMonthsNumber = months;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Calendar getDeadline() {
		return this.deadline;
	}
	
	public Calendar getCreationDate() {
		return this.creationDate;
	}
	
	public String getProjectState() {		
		return this.projectState.toString();
	}
	
	public List<Programmer> getProgrammers() {
		return this.programmers;
	}
	
	public TechnicalTask getTechnicalTask() {
		return this.technicalTask;
	}
	
	public int getDeadlineMonthsNumber() {		
		return this.deadlineMonthsNumber;
	}
	
}
