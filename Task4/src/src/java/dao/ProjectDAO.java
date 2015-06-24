package src.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import src.java.model.Project;
import src.java.model.Programmer;
import src.java.model.Task;
import src.java.model.TechnicalTask;

public class ProjectDAO extends AbstractDAO<Project>  {

	public static final String SQL_SELECT_ALL_PROJECTS = "SELECT project.*,technical_task.title AS tech_title,technical_task.description AS tech_description FROM project INNER JOIN technical_task ON project.technical_task_id=technical_task.id";
	public static final String SQL_SELECT_PROGRAMMERS_BY_PROJECT_ID = "SELECT * FROM users INNER JOIN programmer_params ON users.id=programmer_params.user_id WHERE programmer_params.project_id=?";
	public static final String SQL_SELECT_TASKS_BY_PROJECT_ID = "SELECT * FROM task INNER JOIN project ON task.technical_task_id=project.technical_task_id WHERE project.id=?";
	public static final String SQL_SELECT_TECHNICAL_TASKS_WITH_NO_PROJECT = "SELECT technical_task.* FROM technical_task LEFT JOIN project ON technical_task.id=project.technical_task_id WHERE project.technical_task_id IS NULL";
	public static final String SQL_GET_CUSTOMER_NAME_BY_TECHNICAL_TASK_ID = "SELECT first_name,last_name FROM users INNER JOIN technical_task ON users.id=technical_task.customer_id WHERE technical_task.id=?";
	public static final String SQL_SELECT_TASKS_BY_TECHNICAL_TASK_ID =	"SELECT * FROM task INNER JOIN technical_task ON task.technical_task_id=technical_task.id WHERE technical_task.id=?";
	public static final String SQL_INSERT_NEW_PROJECT =	"INSERT INTO project (technical_task_id,title,description,deadline,register_date,current_state) VALUES (?,?,?,?,NOW(),'WAIT_FOR_PAYMENT')";
	public static final String SQL_GET_LAST_PROJECT_ID = "SELECT MAX(ID) AS max_id FROM project";
	public static final String SQL_ASSIGN_PROGRAMMERS_FOR_PROJECT = "UPDATE programmer_params SET project_id=? WHERE user_id=?";
	
	
	public ProjectDAO() {
		super();
	}
	
	public List<Project> findAll() {
		
		List<Project> projectList = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PROJECTS);
			
			while (resultSet.next()) {				
				Project project = new Project();
				int projectId = resultSet.getInt("id");
				project.setId(projectId);
				project.setTitle(resultSet.getString("title"));
				project.setDescription(resultSet.getString("description"));
				Calendar calendar = new GregorianCalendar();
				java.util.Date date = resultSet.getDate("register_date");
				if (date != null) {
					calendar.setTime(date);
					project.setCreationDate(calendar);
				}
				date = resultSet.getDate("deadline");
				if (date != null) {
					calendar.setTime(date);
					project.setDeadline(calendar);
				}				
				project.setProjectState(resultSet.getString("current_state"));
				project.setProgrammers(getProgrammersByProjectId(connection, projectId));
				TechnicalTask technicalTask = new TechnicalTask();
				technicalTask.setTitle(resultSet.getString("tech_title"));
				technicalTask.setDescription(resultSet.getString("tech_description"));
				technicalTask.setTasks(getTasksByProjectId(connection, projectId));
				projectList.add(project);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return projectList;
	}
	
	
	private List<Programmer> getProgrammersByProjectId(Connection connection, int projectId) {
		List<Programmer> programmers = new ArrayList<>();		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_PROGRAMMERS_BY_PROJECT_ID);
			statement.setInt(1, projectId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Programmer programmer = new Programmer();
				programmer.setFirstName(resultSet.getString("first_name"));
				programmer.setLastName(resultSet.getString("last_name"));
				programmers.add(programmer);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return programmers;
	}
		
	private List<Task> getTasksByProjectId(Connection connection, int projectId) {
		List<Task> tasks = new ArrayList<>();		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_TASKS_BY_PROJECT_ID);
			statement.setInt(1, projectId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Task task = new Task();
				task.setTechnology(resultSet.getString("technology"));
				task.setSkillLevel(resultSet.getString("skill_level"));
				task.setProgrammersNumber(resultSet.getInt("amount"));
				tasks.add(task);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return tasks;
	}
		
	public List<TechnicalTask> getTechnicalTasksWithNoProject() {		
			
		List<TechnicalTask> technicalTasks = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		try {	
			connection = ConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_TECHNICAL_TASKS_WITH_NO_PROJECT);
			
			while (resultSet.next()) {
				TechnicalTask technicalTask = new TechnicalTask();
				int technicalTaskId = resultSet.getInt("id");
				technicalTask.setId(technicalTaskId);
				technicalTask.setTitle(resultSet.getString("title"));
				technicalTask.setDescription(resultSet.getString("description"));
				Calendar calendar = new GregorianCalendar();
				java.util.Date date = resultSet.getDate("register_date");
				if (date != null) {
					calendar.setTime(date);
					technicalTask.setCreationDate(calendar);
				}				
				technicalTask.setTasks(getTasksByTechnicalTaskId(connection, technicalTaskId));
				technicalTask.setCustomerName(getCustomerNameByTechnicalTaskId(connection, technicalTaskId));				
				technicalTasks.add(technicalTask);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return technicalTasks;
	}
	
	private List<Task> getTasksByTechnicalTaskId(Connection connection, int technicalTaskId) {
		List<Task> tasks = new ArrayList<>();		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_TASKS_BY_TECHNICAL_TASK_ID);
			statement.setInt(1, technicalTaskId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Task task = new Task();
				task.setTechnology(resultSet.getString("technology"));
				task.setSkillLevel(resultSet.getString("skill_level"));
				task.setProgrammersNumber(resultSet.getInt("amount"));
				tasks.add(task);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return tasks;
	}
		
	private String getCustomerNameByTechnicalTaskId(Connection connection, int technicalTaskId) {
		String name = "";		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_GET_CUSTOMER_NAME_BY_TECHNICAL_TASK_ID);
			statement.setInt(1, technicalTaskId);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			name = resultSet.getString("first_name")+" "+resultSet.getString("last_name");					
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return name;
	}
	
	public boolean create(int technicalTaskId, Project project) {
		boolean success = false;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_NEW_PROJECT);
			statement.setInt(1, technicalTaskId);
			statement.setString(2, project.getTitle());
			statement.setString(3, project.getDescription());			
			statement.setDate(4, new java.sql.Date(Project.countDeadline(project.getDeadlineMonthsNumber()).getTimeInMillis()));
			statement.executeUpdate();

			assignProgrammersForProject(connection, project.getProgrammers());
			success = true;
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return success;
	}
	
	private void assignProgrammersForProject(Connection connection, List<Programmer> programmers) {
		
		PreparedStatement statement = null;
		for(int i=0; i<programmers.size(); i++) {
			try {
				Programmer currentProgr = programmers.get(i);
				statement = connection.prepareStatement(SQL_ASSIGN_PROGRAMMERS_FOR_PROJECT);
				statement.setInt(1, getLastProjectId(connection));
				statement.setInt(2, currentProgr.getId());				
				statement.executeUpdate();				
			} catch (SQLException e) {
				System.err.println("SQL exception (request or table failed): " + e);
			} finally {
				close(statement);		
			}
		}		
		
	}
	
	private int getLastProjectId(Connection connection) {
		
		int id = 0;
		Statement statement = null;
		try {			
			statement = connection.createStatement();			
			ResultSet resultSet = statement.executeQuery(SQL_GET_LAST_PROJECT_ID);
			resultSet.next();
			id = resultSet.getInt("max_id");
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return id;
	}
	
	
}
