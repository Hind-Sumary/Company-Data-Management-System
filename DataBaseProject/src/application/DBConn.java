package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

class DBConn {

	private static Connection connectionString;
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "qazwsxedc";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "project";

	DBConn(String URL, String port, String dbName, String dbUsername, String dbPassword) {

		this.URL = URL;
		this.port = port;
		this.dbName = dbName;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}

	public Connection connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		System.out.println(dbURL);

		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");

		Class.forName("com.mysql.jdbc.Driver");
		// new com.mysql.jdbc.Driver();

		connectionString = (Connection) DriverManager.getConnection(dbURL, p);

		return connectionString;
	}
	
	 public void closeConnection() throws SQLException {
	        if (connectionString != null && !connectionString.isClosed()) {
	        	connectionString.close();
	        }
	    }
}