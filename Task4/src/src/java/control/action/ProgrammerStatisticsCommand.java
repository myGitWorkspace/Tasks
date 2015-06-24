package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.ProgrammerStatisticsDAO;
import src.java.model.Project;
import src.java.model.User;

public class ProgrammerStatisticsCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
		
		ProgrammerStatisticsDAO programmerDAO = new ProgrammerStatisticsDAO();
		User currentUser = (User)request.getSession(false).getAttribute("user");
		int userId = currentUser.getId();
		request.setAttribute("userId", userId);
		request.setAttribute("userName", currentUser.getFirstName());
		
		Project project = programmerDAO.getProjectByProgrammerId(userId);
		request.setAttribute("project", project);
		
		
		String page = ConfigurationManager.getProperty("path.page.ProgrammerStatistics");
		
		return page;
	}
}
