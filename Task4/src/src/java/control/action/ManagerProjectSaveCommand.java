package src.java.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import src.java.dao.ProjectDAO;
import src.java.model.Project;
import src.java.model.Programmer;

public class ManagerProjectSaveCommand implements ActionCommand {
	@Override
	public String execute(HttpServletRequest request) {		
		
		Project project = new Project();
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int monthsNumber = Integer.parseInt(request.getParameter("deadline"));
		project.setTitle(title);
		project.setDescription(description);
		project.setDeadlineMonthsNumber(monthsNumber);
		int tasksCount = Integer.parseInt(request.getParameter("tasksCount"));
		int technicalTaskId = Integer.parseInt(request.getParameter("technicalTaskId"));
		
		List<Programmer> programmers = new ArrayList<>();
		for(int i=1; i<tasksCount+1; i++) {
			int numberProgrammers = Integer.parseInt(request.getParameter("number_selected_programmers"+i));
			for(int j=1; j<numberProgrammers+1; j++) {
				String requestParam = request.getParameter("programmer"+i+"_id"+j);
				int programmerId = 0;
				if (requestParam != null)
					programmerId = Integer.parseInt(requestParam);
				Programmer programmer = new Programmer();
				programmer.setId(programmerId);
				programmers.add(programmer);
			}
		}
		project.setProgrammers(programmers);
		
		ProjectDAO projectDAO = new ProjectDAO();
		projectDAO.create(technicalTaskId, project);
		
		String page = new ManagerProjectListCommand().execute(request);
		
		return page;
	}
}
