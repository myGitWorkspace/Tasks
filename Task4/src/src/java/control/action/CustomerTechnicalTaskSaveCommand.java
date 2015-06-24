package src.java.control.action;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.TechnicalTaskDAO;
import src.java.model.TechnicalTask;
import src.java.model.Task;

public class CustomerTechnicalTaskSaveCommand implements ActionCommand  {
	@Override
	public String execute(HttpServletRequest request) {		
		
		TechnicalTask technicalTask = new TechnicalTask();
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		technicalTask.setTitle(title);
		technicalTask.setDescription(description);
		int tasksCount = Integer.parseInt(request.getParameter("tasksCount"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		List<Task> tasks = new ArrayList<>();
		for(int i=1; i<tasksCount+1; i++) {
			Task task = new Task();
			String technology = request.getParameter("technology"+i);
			String skillLevel = request.getParameter("skill_level"+i);
			int amount = Integer.parseInt(request.getParameter("amount"+i));
			task.setTechnology(technology);
			task.setSkillLevel(skillLevel);
			task.setProgrammersNumber(amount);
			tasks.add(task);
		}
		technicalTask.setTasks(tasks);
		
		TechnicalTaskDAO technicalTaskDAO = new TechnicalTaskDAO();
		technicalTaskDAO.create(userId, technicalTask);
		
		String page = new CustomerTechnicalTaskListCommand().execute(request);
		
		return page;
	}
}
