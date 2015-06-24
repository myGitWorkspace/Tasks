package src.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import src.java.model.TechnicalTask;
import src.java.model.Task;
import src.java.model.Programmer;

public class TechnicalTaskDAO extends AbstractDAO<TechnicalTask> {
	
	public static final String SQL_SELECT_ALL_TECHNICAL_TASKS = "SELECT * FROM technical_task";
	public static final String SQL_SELECT_TECHNICAL_TASK_BY_CUSTOMER_ID =	"SELECT * FROM technical_task WHERE customer_id=?";
	public static final String SQL_SELECT_TASKS_BY_TECHNICAL_TASK_ID =	"SELECT * FROM task INNER JOIN technical_task ON task.technical_task_id=technical_task.id WHERE technical_task.id=?";
	public static final String SQL_SELECT_PROGRAMMERS_THAT_MATCH_TASK_BY_TASK_ID =	"SELECT users.* FROM task,users INNER JOIN programmer_params ON users.id=programmer_params.user_id WHERE programmer_params.technology=task.technology AND programmer_params.skill_level=task.skill_level AND task.id=?";
	public static final String SQL_SELECT_DEADLINE_BY_PROJECT_ID =	"SELECT deadline FROM project INNER JOIN technical_task ON project.technical_task_id=technical_task.id WHERE technical_task.customer_id=?";
	public static final String SQL_SELECT_HOURS_OF_WORK_BY_PROJECT_ID =	"SELECT * FROM programmer_statistics INNER JOIN project ON programmer_statistics.project_id=project.id INNER JOIN technical_task ON project.technical_task_id=technical_task.id WHERE technical_task.customer_id=?";
	public static final String SQL_INSERT_NEW_TECHNICAL_TASK =	"INSERT INTO technical_task (customer_id,title,description,register_date,current_state) VALUES (?,?,?,NOW(),'WAIT')";
	public static final String SQL_INSERT_NEW_TASK = "INSERT INTO task (technical_task_id,technology,skill_level,amount) VALUES (?,?,?,?)";
	public static final String SQL_GET_LAST_TECHNICAL_TASK_ID = "SELECT MAX(ID) AS max_id FROM technical_task";
	
	public TechnicalTaskDAO() {
		super();
	}
	
	public List<TechnicalTask> findAll() {
		
		List<TechnicalTask> technicalTaskList = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TECHNICAL_TASKS);
			
			while (resultSet.next()) {
				TechnicalTask technicalTask = new TechnicalTask();
				technicalTask.setTitle(resultSet.getString("title"));
				technicalTask.setDescription(resultSet.getString("description"));
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(resultSet.getDate("register_date"));
				technicalTask.setCreationDate(calendar);
				technicalTaskList.add(technicalTask);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return technicalTaskList;
	}
	
	public boolean create(int customerId, TechnicalTask technicalTask) {
		
		boolean success = false;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_NEW_TECHNICAL_TASK);
			statement.setInt(1, customerId);
			statement.setString(2, technicalTask.getTitle());
			statement.setString(3, technicalTask.getDescription());
			statement.executeUpdate();			
			createTasks(connection, technicalTask.getTasks());
			success = true;
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return success;
	}
	
	private boolean createTasks(Connection connection, List<Task> tasks) {
		boolean success = false;
		PreparedStatement statement = null;
		for(int i=0; i<tasks.size(); i++) {
			try {
				Task currentTask = tasks.get(i);
				statement = connection.prepareStatement(SQL_INSERT_NEW_TASK);
				statement.setInt(1, getLastTechnicalTaskId(connection));
				statement.setString(2, currentTask.getTechnology());
				statement.setString(3, currentTask.getSkillLevel());
				statement.setInt(4, currentTask.getProgrammersNumber());
				statement.executeUpdate();			
				success = true;
			} catch (SQLException e) {
				System.err.println("SQL exception (request or table failed): " + e);
			} finally {
				close(statement);		
			}
		}		
		return success;
	}
	
	private int getLastTechnicalTaskId(Connection connection) {
		
		int id = 0;
		Statement statement = null;
		try {			
			statement = connection.createStatement();			
			ResultSet resultSet = statement.executeQuery(SQL_GET_LAST_TECHNICAL_TASK_ID);
			resultSet.next();
			id = resultSet.getInt("max_id");
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return id;
	}
	
	public List<TechnicalTask> findTechnicalTaskByCustomerId(int customerId) {
		List<TechnicalTask> technicalTaskList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_SELECT_TECHNICAL_TASK_BY_CUSTOMER_ID);
			statement.setInt(1, customerId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				TechnicalTask technicalTask = new TechnicalTask();
				int technicalTaskId = resultSet.getInt("id");
				technicalTask.setId(technicalTaskId);
				technicalTask.setTitle(resultSet.getString("title"));
				technicalTask.setDescription(resultSet.getString("description"));
				Calendar calendar = new GregorianCalendar();
				java.util.Date regDate = resultSet.getDate("register_date");
				if (regDate != null) {
					calendar.setTime(regDate);
					technicalTask.setCreationDate(calendar);
				}
				
				technicalTask.setTechnicalTaskState(resultSet.getString("current_state"));
				technicalTask.setTasks(getTasksByTechnicalTaskId(connection, technicalTaskId));
				technicalTask.setDeadline(getDeadlineByCustomerId(connection, customerId));
				technicalTask.setWorkHoursDaily(getHoursOfWorkByCustomerId(connection, customerId));
				technicalTaskList.add(technicalTask);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return technicalTaskList;
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
	
	public List<Task> getTasksByTechnicalTaskIdWithProgrammers(int technicalTaskId) {
		List<Task> tasks = new ArrayList<>();		
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_SELECT_TASKS_BY_TECHNICAL_TASK_ID);
			statement.setInt(1, technicalTaskId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Task task = new Task();
				task.setTechnology(resultSet.getString("technology"));
				task.setSkillLevel(resultSet.getString("skill_level"));
				task.setProgrammersNumber(resultSet.getInt("amount"));
				task.setProgrammers(getProgrammersThatMatchTaskByTaskId(connection,resultSet.getInt("id")));
				tasks.add(task);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return tasks;
	}
	
	private List<Programmer> getProgrammersThatMatchTaskByTaskId(Connection connection, int taskId) {
		List<Programmer> programmers = new ArrayList<>();		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_PROGRAMMERS_THAT_MATCH_TASK_BY_TASK_ID);
			statement.setInt(1, taskId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Programmer programmer = new Programmer();
				programmer.setFirstName(resultSet.getString("first_name"));
				programmer.setId(resultSet.getInt("id"));
				programmers.add(programmer);
			}
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return programmers;
	}
	
	private Calendar getDeadlineByCustomerId(Connection connection, int customerId) {
		Calendar deadline = null;		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_DEADLINE_BY_PROJECT_ID);
			statement.setInt(1, customerId);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Calendar calendar = new GregorianCalendar();
			java.util.Date date = resultSet.getDate("deadline");
			if (date != null) {
				calendar.setTime(date);
				deadline = calendar;
			}	
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return deadline;
	}
	
	private Map<Calendar,Integer> getHoursOfWorkByCustomerId(Connection connection, int customerId) {
		Map<Calendar,Integer> resultMap = new HashMap<>();		
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(SQL_SELECT_HOURS_OF_WORK_BY_PROJECT_ID);
			statement.setInt(1, customerId);
			ResultSet resultSet = statement.executeQuery();
			
			Map<Calendar,Integer> rawResults = new HashMap<>();
			Calendar calendar = new GregorianCalendar();
			while (resultSet.next()) {				
				calendar.setTime(resultSet.getDate("date"));
				rawResults.put(calendar, resultSet.getInt("work_hours"));
			}
		    
			Iterator iterator1 = rawResults.entrySet().iterator();
		    for (int i=0; i<rawResults.size(); i++) {
		    	Calendar currentDate = (Calendar)((Map.Entry)iterator1.next()).getKey();
		    	if (resultMap.containsKey( currentDate ) )
		    		continue;
		    	int sumAmount = 0;
		    	int counter = 0;
		    	Iterator iterator2 = rawResults.entrySet().iterator();		    	
		    	while (iterator2.hasNext()) {
			        Map.Entry entry = (Map.Entry)iterator2.next();
			        if (entry.getKey() == currentDate) {
			        	sumAmount += (int)entry.getValue();
			        	counter++;
			        }
			    }
		    	resultMap.put(currentDate, sumAmount/counter);
		    }		    
		
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return resultMap;
	}
	
	
}
