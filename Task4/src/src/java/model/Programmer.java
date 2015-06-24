package src.java.model;

enum ProgrammerState {
	FREE, BUSY
}

public class Programmer extends User {
	
	private int id;
	private Technology technology;
	private SkillLevel skillLevel;
	private double costPerDay;
	private ProgrammerState programmerState;
	private Project project;
	private int hoursInWorkForProject;
	
	public Programmer() {
	}
	
	public Programmer(int id, String login, String password, String name, String lastName, Project project, Technology technology, SkillLevel skillLevel ) {
		super(id, login, password, name, lastName);
		this.project = project;		
		this.technology = technology;
		this.skillLevel = skillLevel;
		if (this.project == null)
			programmerState = ProgrammerState.FREE;
		else
			programmerState = ProgrammerState.BUSY;
		costPerDay = countDayProfit();
	}
	
	private double countDayProfit() {
		return technology.getPrice()*skillLevel.getMultiplier();
	}
	
	public boolean isWaitingForProject() {
		if (programmerState == ProgrammerState.FREE)
			return true;
		return false;
	}
	
	public void setProgrammerForProject(Project project) throws SettingProgrammerForProjectException {
		if (programmerState == ProgrammerState.FREE) {
			this.project = project;
			programmerState = ProgrammerState.BUSY;			
		} else 
			throw new SettingProgrammerForProjectException("Programmer " + this.firstName + " is busy for another project");
	}
	
	public void finishProject() {
		this.project = null;
		programmerState = ProgrammerState.FREE;		
	}
	
	public String getTechnology() {
		return technology.toString();
	}
	
	public String getSkillLevel() {
		return skillLevel.toString();
	}
	
	public int getId() {
		return id;
	}
	
	public void setTechnology(String technologyStr) {
		Technology tech = Technology.valueOf(technologyStr.toUpperCase());
		this.technology = tech;
	}
	
	public void setSkillLevel(String skillLevelStr) {
		SkillLevel skill = SkillLevel.valueOf(skillLevelStr.toUpperCase());
		this.skillLevel = skill;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
