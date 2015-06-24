package src.java.control.action;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.ProgrammerStatisticsDAO;

public class SaveProgrammerStatisticsCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {
		
		ProgrammerStatisticsDAO programmerDAO = new ProgrammerStatisticsDAO();
		int programmerId = Integer.parseInt(request.getParameter("programmerId"));
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		int workHours = Integer.parseInt(request.getParameter("hours_number"));
		programmerDAO.saveProgrammerStatisticsForToday(programmerId, projectId, workHours);
		
		String page = new ProgrammerStatisticsCommand().execute(request);
		
		return page;
	}
}
