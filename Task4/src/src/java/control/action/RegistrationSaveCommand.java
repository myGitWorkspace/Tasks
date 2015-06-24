package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.UserDAO;
import src.java.model.Programmer;
import src.java.model.User;
import src.java.model.UserType;

public class RegistrationSaveCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		
		UserType userType = UserType.valueOf(request.getParameter("user_type").toUpperCase());
		User user;
		if (userType == UserType.PROGRAMMER) {
			user = new Programmer();			
			String technology = request.getParameter("technology");
			String skill_level = request.getParameter("skill_level");
			((Programmer)user).setTechnology(technology);
			((Programmer)user).setSkillLevel(skill_level);
		} else
			user = new User();		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String password = request.getParameter("password");		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		user.setUserType(userType.ordinal()+1);		
		
		UserDAO userDAO = new UserDAO();
		userDAO.create(user);
		
		String page = ConfigurationManager.getProperty("path.page.login");
		return page;
	}
	
}
