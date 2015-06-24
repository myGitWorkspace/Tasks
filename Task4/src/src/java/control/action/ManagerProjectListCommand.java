package src.java.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.ProjectDAO;
import src.java.dao.TechnicalTaskDAO;
import src.java.model.TechnicalTask;
import src.java.model.User;
import src.java.model.Project;

public class ManagerProjectListCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
		
		User currentUser = (User)request.getSession(false).getAttribute("user");
		int userId = currentUser.getId();
		request.setAttribute("userId", userId);
		request.setAttribute("userName", currentUser.getFirstName());
		
		ProjectDAO projectDAO = new ProjectDAO();
		List<Project> projects = projectDAO.findAll();
		List<TechnicalTask> technicalTasks = projectDAO.getTechnicalTasksWithNoProject();		
		request.setAttribute("projects", projects);
		request.setAttribute("technicalTasks", technicalTasks);
				
		String page = ConfigurationManager.getProperty("path.page.ManagerProjectList");
		
		return page;
	}
}
