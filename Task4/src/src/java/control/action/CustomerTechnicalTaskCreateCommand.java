package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.model.User;

public class CustomerTechnicalTaskCreateCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
				
		User currentUser = (User)request.getSession(false).getAttribute("user");
		int userId = currentUser.getId();
		request.setAttribute("userId", userId);
		request.setAttribute("userName", currentUser.getFirstName());
		
		String page = ConfigurationManager.getProperty("path.page.CustomerTechnicalTaskCreate");
		return page;
	}
}
