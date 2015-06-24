package src.java.control.action;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.TechnicalTaskDAO;
import src.java.model.Project;
import src.java.model.Task;
import src.java.model.TechnicalTask;
import src.java.model.User;

public class ManagerProjectCreateCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
				
		User currentUser = (User)request.getSession(false).getAttribute("user");
		int userId = currentUser.getId();
		int technicalTaskId = Integer.parseInt(request.getParameter("technicalTaskId"));
		request.setAttribute("userId", userId);
		request.setAttribute("userName", currentUser.getFirstName());
		request.setAttribute("tasksCount", request.getParameter("tasksCount"));
		request.setAttribute("technicalTaskId", technicalTaskId);
		request.setAttribute("technicalTaskTitle", request.getParameter("technicalTaskTitle"));
		request.setAttribute("technicalTaskDescription", request.getParameter("technicalTaskDescription"));
				
		TechnicalTaskDAO technicalTaskDAO = new TechnicalTaskDAO();
		List<Task> tasks = new ArrayList<>();		
		tasks = technicalTaskDAO.getTasksByTechnicalTaskIdWithProgrammers(technicalTaskId);
		request.setAttribute("tasks", tasks);
		
		String page = ConfigurationManager.getProperty("path.page.ManagerProjectCreate");
		return page;
	}
}
