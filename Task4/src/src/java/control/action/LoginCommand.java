package src.java.control.action;

import javax.servlet.http.HttpServletRequest;
import src.java.dao.UserDAO;
import src.java.model.User;
import src.java.model.UserType;

public class LoginCommand implements ActionCommand {
	
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;

		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);

		UserDAO userDAO = new UserDAO();
		User currentUser = userDAO.userAuthenticate(login,pass);
		
		if (currentUser != null) {
			request.getSession(true).setAttribute("user", currentUser);
			//request.setAttribute("user", currentUser);
			UserType userType = UserType.GUEST;
			switch(currentUser.getUserType()) {
				case 1: userType = UserType.PROGRAMMER; break;
				case 2: userType = UserType.CUSTOMER; break;
				case 3: userType = UserType.MANAGER; break;
				default: userType = UserType.GUEST;
			}
			
			switch(userType) {
				case PROGRAMMER: page = new ProgrammerStatisticsCommand().execute(request); break;
				case CUSTOMER: page = new CustomerTechnicalTaskListCommand().execute(request); break;
				case MANAGER: page = new ManagerProjectListCommand().execute(request); break;
				case GUEST: page = ConfigurationManager.getProperty("path.page.login"); break;			
			}			
						
		} else {
			request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
			page = ConfigurationManager.getProperty("path.page.login");
		}
		return page;
	}
}
