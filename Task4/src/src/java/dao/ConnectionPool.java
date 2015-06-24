package src.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	
	private static final String DATASOURCE_NAME = "jdbc/task4";
	
	private static DataSource dataSource;
	
	static {
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource)initContext.lookup("java:comp/env/jdbc/task4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private ConnectionPool() { }
	
	public static Connection getConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		return connection;
	}
	
}
