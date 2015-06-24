package src.java.model;

import java.util.List;
import java.util.ArrayList;

public class ITCompany {

	private List<Programmer> programmers = new ArrayList<>();
	private List<TechnicalTask> technicalTasks = new ArrayList<>();
	private List<Project> projects = new ArrayList<>();
	private List<Bill> bills = new ArrayList<>();
	
	public ITCompany(List<Programmer> programmers, List<TechnicalTask> technicalTasks, List<Project> projects, List<Bill> bills) {
		if (programmers != null)
			this.programmers.addAll(programmers);
		if (technicalTasks != null)
			this.technicalTasks.addAll(technicalTasks);
		if (projects != null)
			this.projects.addAll(projects);
		if (bills != null)
			this.bills.addAll(bills);
	}
	
	public void addTechnicalTask(TechnicalTask technicalTask) {
		technicalTasks.add(technicalTask);
		makeProject(technicalTask);
	}
	
	private void makeProject(TechnicalTask technicalTask) {
		Project project = null;
		try {
			project = new Project(technicalTask, this);
			projects.add(project);
			makeBill(project);
		} catch(NotEnoughProgrammersForProjectException e) {
			e.printStackTrace();
		}				
	}
	
	private void makeBill(Project project) {
		Bill bill = new Bill(project);
		bills.add(bill);
	}
	
	public List<Programmer> findFreeProgrammers(String technologyStr, String skillLevelStr) {

		List<Programmer> resultList = new ArrayList<>();
		for(Programmer programmer : programmers) {
			if ( (programmer.getTechnology() == technologyStr.toUpperCase()) && (programmer.getSkillLevel() == skillLevelStr.toUpperCase()) && programmer.isWaitingForProject() )
				resultList.add(programmer);
		}
		if (resultList.size() == 0)
			return null;
		else 
			return resultList;		
	}
	
}
