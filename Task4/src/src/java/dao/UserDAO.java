package src.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import src.java.model.User;
import src.java.model.Programmer;

public class UserDAO extends AbstractDAO<User> {
	
	public static final String SQL_CREATE_NEW_USER = "INSERT INTO users (user_type_id,first_name,last_name,login,email,password,register_date) VALUES (?,?,?,?,?,?,NOW())";
	public static final String SQL_ADD_USER_PROGRAMMER_PARAMS = "INSERT INTO programmer_params (user_id,project_id,technology,skill_level) VALUES (?,0,?,?)";
	public static final String SQL_GET_LAST_USER_ID = "SELECT MAX(ID) AS max_id FROM users";
	public static final String SQL_USER_AUTHENTICATE = "SELECT * FROM users WHERE login=? AND password=?";

	public UserDAO() {
		super();
	}
	
	public boolean create( User user) {
	
		boolean success = false;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_CREATE_NEW_USER);
			statement.setInt(1, user.getUserType());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getLogin());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getPassword());			
			statement.executeUpdate();			
			if (user.getUserType() == 1) {
				Programmer programmer = (Programmer)user;
				addUserProgrammerParams(connection, programmer.getTechnology(), programmer.getSkillLevel());
			}
			success = true;
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return success;
	}
	
	private void addUserProgrammerParams(Connection connection, String technology, String skillLevel) {
		
		PreparedStatement statement = null;		
		try {			
			statement = connection.prepareStatement(SQL_ADD_USER_PROGRAMMER_PARAMS);
			statement.setInt(1, getLastUserId(connection));
			statement.setString(2, technology);
			statement.setString(3, skillLevel);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
	}
	
	private int getLastUserId(Connection connection) {
		int userId = 0;		
		Statement statement = null;
		try {
			statement = connection.createStatement();			
			ResultSet resultSet = statement.executeQuery(SQL_GET_LAST_USER_ID);			
			resultSet.next();
			userId = resultSet.getInt("max_id");					
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return userId;
	}
	
	public User userAuthenticate(String login, String password) {
		User currentUser = null;		
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			statement = connection.prepareStatement(SQL_USER_AUTHENTICATE);
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();			
			resultSet.next();
			if(resultSet != null) {
				currentUser = new User();
				currentUser.setId(resultSet.getInt("id"));
				currentUser.setUserType(resultSet.getInt("user_type_id"));
				currentUser.setFirstName(resultSet.getString("first_name"));
				currentUser.setLastName(resultSet.getString("last_name"));
				currentUser.setLogin(resultSet.getString("login"));
				currentUser.setEmail(resultSet.getString("email"));	
			}					
		} catch (SQLException e) {
			System.err.println("SQL exception (request or table failed): " + e);
		} finally {
			close(statement);		
		}
		return currentUser;
	}
	
	public List<User> findAll() {
		throw new UnsupportedOperationException();
	}
	
}
