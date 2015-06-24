package src.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import src.java.model.Project;

public class ProgrammerStatisticsDAO extends AbstractDAO<Project> {

	public static final String SQL_SELECT_PROJECT_BY_PROGRAMMER_ID = "SELECT * FROM project INNER JOIN programmer_statistics ON project.id=programmer_statistics.project_id WHERE programmer_id=?";
	public static final String SQL_SAVE_PROGRAMMER_STATISTICS_FOR_TODAY = "INSERT INTO programmer_statistics (programmer_id,project_id,date,work_hours) VALUES (?,?,NOW(),?)";
	
	public ProgrammerStatisticsDAO() {
		super();
	}
	
	public Project getProjectByProgrammerId(int programmerId) {
		Project project = null;		
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_SELECT_PROJECT_BY_PROGRAMMER_ID);
			statement.setInt(1, programmerId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet != null) {
				project = new Project();
				resultSet.next();
				project.setId(resultSet.getInt("id"));
				project.setTitle(resultSet.getString("title"));
				project.setDescription(resultSet.getString("description"));
				Calendar calendar = new GregorianCalendar();
				java.util.Date deadline = resultSet.getDate("deadline");
				if (deadline != null) {
					calendar.setTime(resultSet.getDate("deadline"));
					project.setDeadline(calendar);					
				}
			}
			
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return project;
	}
	
	public void saveProgrammerStatisticsForToday(int programmerId, int projectId, int workHours) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();			
			statement = connection.prepareStatement(SQL_SAVE_PROGRAMMER_STATISTICS_FOR_TODAY);
			statement.setInt(1, programmerId);
			statement.setInt(2, projectId);
			statement.setInt(3, workHours);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
	}
	
	public List<Project> findAll() {
		throw new UnsupportedOperationException();
	}
	
}
