package src.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO <T> {
	protected Connection connection;
	
	public AbstractDAO() {		
	}
	
	public abstract List<T> findAll();	
	
	public void close(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
		}	
	}
	
}
