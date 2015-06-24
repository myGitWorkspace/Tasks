package src.java.model;

import java.util.List;
import java.util.ArrayList;

public class ITCompanyTest {

	public static void main(String[] args) {
		
		Customer customer = new Customer(1,"login","pass","name","lastname");
		Manager manager = new Manager(1,"login","pass","name","lastname");
		Programmer programmer1 = new Programmer(1,"login","pass","name","lastname",null,Technology.C_PLUS_PLUS,SkillLevel.JUNIOR);
		Programmer programmer2 = new Programmer(1,"login","pass","name","lastname",null,Technology.HTML,SkillLevel.MIDDLE);
		Programmer programmer3 = new Programmer(1,"login","pass","name","lastname",null,Technology.JAVA,SkillLevel.SENIOR);
		Programmer programmer4 = new Programmer(1,"login","pass","name","lastname",null,Technology.JAVASCRIPT,SkillLevel.SENIOR);
		Programmer programmer5 = new Programmer(1,"login","pass","name","lastname",null,Technology.PHP,SkillLevel.MIDDLE);
		Programmer programmer6 = new Programmer(1,"login","pass","name","lastname",null,Technology.PYTHON,SkillLevel.MIDDLE);
		Programmer programmer7 = new Programmer(1,"login","pass","name","lastname",null,Technology.SQL,SkillLevel.JUNIOR);
		Programmer programmer8 = new Programmer(1,"login","pass","name","lastname",null,Technology.JAVA,SkillLevel.JUNIOR);
		Programmer programmer9 = new Programmer(1,"login","pass","name","lastname",null,Technology.HTML,SkillLevel.MIDDLE);
		Programmer programmer10 = new Programmer(1,"login","pass","name","lastname",null,Technology.C_PLUS_PLUS,SkillLevel.MIDDLE);
		Programmer programmer11 = new Programmer(1,"login","pass","name","lastname",null,Technology.JAVASCRIPT,SkillLevel.SENIOR);
		Programmer programmer12 = new Programmer(1,"login","pass","name","lastname",null,Technology.PHP,SkillLevel.SENIOR);
		List<Programmer> programmers = new ArrayList<Programmer>();
		programmers.add(programmer1);
		programmers.add(programmer2);
		programmers.add(programmer3);
		programmers.add(programmer4);
		programmers.add(programmer5);
		programmers.add(programmer6);
		programmers.add(programmer7);
		programmers.add(programmer8);
		programmers.add(programmer9);
		programmers.add(programmer10);
		programmers.add(programmer11);
		programmers.add(programmer12);
		
		ITCompany itCompany = new ITCompany(programmers,null,null,null);
		TechnicalTask technicalTask = new TechnicalTask(1,"title", "descr", null);
		Task task1 = new Task(Technology.JAVA, SkillLevel.JUNIOR,1);
		Task task2 = new Task(Technology.C_PLUS_PLUS, SkillLevel.JUNIOR,1);
		Task task3 = new Task(Technology.HTML, SkillLevel.SENIOR,1);
		technicalTask.addTask(task1);
		technicalTask.addTask(task2);
		technicalTask.addTask(task3);
		itCompany.addTechnicalTask(technicalTask);
	}
	
}
