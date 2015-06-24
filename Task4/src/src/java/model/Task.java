package src.java.model;

import java.util.List;
import java.util.ArrayList;

enum Technology {
	JAVA(100),PYTHON(80),JAVASCRIPT(50),PHP(40),SQL(40),C_PLUS_PLUS(70),HTML(30);
	private int price;
	Technology(int price) {
		this.price = price;
	}
	public int getPrice() {
		return this.price;
	}
}

enum SkillLevel {
	JUNIOR(1.4),MIDDLE(1.9),SENIOR(2.3);
	private double multiplier;
	SkillLevel(double multiplier) {
		this.multiplier = multiplier;
	}
	public double getMultiplier() {
		return this.multiplier;
	}
}

public class Task {
	
	private Technology technology;
	private SkillLevel skillLevel;
	private int programmersNumber;
	private List<Programmer> programmers = new ArrayList<>();
	
	public Task() {
	}
	
	public Task(Technology technology, SkillLevel skillLevel, int programmersNumber) {
		this.technology = technology;
		this.skillLevel = skillLevel;
		this.programmersNumber = programmersNumber;
	}
	
	public String getTechnology() {
		return technology.toString();
	}
	
	public String getSkillLevel() {
		return skillLevel.toString();
	}
	
	public int getProgrammersNumber() {
		return programmersNumber;
	}
	
	public List<Programmer> getProgrammers() {
		return programmers;
	}
	
	public void setTechnology(String technology) {
		if (technology != null) {
			Technology tech = Technology.valueOf(technology.toUpperCase());
			this.technology = tech;
		}
	}
	
	public void setSkillLevel(String skillLevel) {
		if (skillLevel != null) {
			SkillLevel skill = SkillLevel.valueOf(skillLevel.toUpperCase());
			this.skillLevel = skill;			
		}
	}
	
	public void setProgrammersNumber(int programmersNumber) {
		this.programmersNumber = programmersNumber;
	}
	
	public void setProgrammers(List<Programmer> list) {
		programmers.addAll(list);
	}
	
}
