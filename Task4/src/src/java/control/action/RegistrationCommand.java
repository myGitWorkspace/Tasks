package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.model.User;

public class RegistrationCommand implements ActionCommand {
	
	@Override
	public String execute(HttpServletRequest request) {
		
		String page = ConfigurationManager.getProperty("path.page.Registration");
		return page;
	}
}
