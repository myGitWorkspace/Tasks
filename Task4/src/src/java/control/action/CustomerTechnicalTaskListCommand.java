package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.TechnicalTaskDAO;
import src.java.model.TechnicalTask;
import src.java.model.User;
import java.util.List;

public class CustomerTechnicalTaskListCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
		
		User currentUser = (User)request.getSession(false).getAttribute("user");
		int userId = currentUser.getId();
		request.setAttribute("userId", userId);
		request.setAttribute("userName", currentUser.getFirstName());
		
		TechnicalTaskDAO technicalTaskDAO = new TechnicalTaskDAO();
		List<TechnicalTask> technicalTask = technicalTaskDAO.findTechnicalTaskByCustomerId(userId);
		request.setAttribute("technicalTask", technicalTask);
		request.setAttribute("tasks", technicalTask);
				
		String page = ConfigurationManager.getProperty("path.page.CustomerTechnicalTaskList");
		
		return page;
	}
}
