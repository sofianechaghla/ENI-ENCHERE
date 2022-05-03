/**
 * PROJET ENI-ENCHERES
 * 
 */
package fr.reddev.encheres.DAL.JDBC;

/**
 * @author REDDEV
 */
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.reddev.encheres.Exception.DALException;

public class JdbcTools {
	private static Connection connection;

	public static Connection getConnection() throws DALException {
		if (connection == null) {
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
				connection = dataSource.getConnection();
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
