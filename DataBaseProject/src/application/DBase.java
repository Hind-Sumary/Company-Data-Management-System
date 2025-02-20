package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBase {

	private static String dbUsername = "root"; // database username
	private static String dbPassword = "qazwsxedc"; // database password
	private static String URL = "127.0.0.1"; // server location
	private static String port = "3306"; // port that mysql uses
	private static String dbName = "project"; // database on mysql to connect to

	private static ObservableList<Employee> empdataList = FXCollections.observableArrayList();
	private static ObservableList<Product> data = FXCollections.observableArrayList();
	private static ObservableList<Department> depdataList = FXCollections.observableArrayList();
	private static ObservableList<Company> companydataList = FXCollections.observableArrayList();
	private static ObservableList<Order> orderList = FXCollections.observableArrayList();

	private static Connection con;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		DBConn dbConn = new DBConn(URL, port, dbName, dbUsername, dbPassword);

		con = dbConn.connectDB();
		System.out.println("Connection established");

		// Call methods to fetch data from each table
		fetchEmployeeData();
		fetchDepartmentData();
		fetchCompanyData();
		fetchProductData();
		fetchOrderData();

		con.close();
		System.out.println("Connection closed");

	}

	static void connectDB() throws ClassNotFoundException, SQLException {

		URL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection(URL, p);

	}

	static void fetchEmployeeData() throws SQLException {
		String SQLtxt = "SELECT * FROM Employee";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);

		empdataList.clear(); // Clear the list to avoid duplicates

		while (rs.next()) {
			Employee employee = new Employee(rs.getString("EmployeeID"), rs.getString("EmployeeName"),
					rs.getDate("DOB"), rs.getDate("EmploymentDate"), rs.getString("EmailAddress"),
					rs.getString("DepartmentID"), rs.getString("PhoneNum"), rs.getString("Address"),
					rs.getDouble("Salary"), rs.getString("BankInfo"));

			empdataList.add(employee);
		}

		rs.close();
		stmt.close();
	}

	static void fetchDepartmentData() throws SQLException {
		String SQLtxt = "SELECT * FROM Department";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);

		depdataList.clear(); // Clear the list to avoid duplicates

		while (rs.next()) {
			Department department = new Department(rs.getString("ID"), rs.getString("DepartmentName"),
					rs.getString("ManagerName"), rs.getString("CompanyID"));

			depdataList.add(department);
		}

		rs.close();
		stmt.close();
	}

	static void fetchCompanyData() throws SQLException {
		String SQLtxt = "SELECT * FROM Company";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);

		companydataList.clear(); // Clear the list to avoid duplicates

		while (rs.next()) {
			Company company = new Company(rs.getString("CompanyID"), rs.getString("CompanyName"));

			companydataList.add(company);
		}

		rs.close();
		stmt.close();
	}

	static void fetchProductData() throws SQLException {
		String SQLtxt = "SELECT * FROM Product";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);

		data.clear(); // Clear the list to avoid duplicates

		while (rs.next()) {
			Product product = new Product(rs.getString("pr_ID"), rs.getString("pr_name"), rs.getDouble("pr_height"),
					rs.getDouble("pr_length"), rs.getDouble("pr_voltage"), rs.getDouble("pr_wattage"),
					rs.getString("pr_color"), rs.getInt("pr_price"), rs.getInt("pr_quantity"),
					rs.getString("SupplierName"));

			data.add(product);
		}

		rs.close();
		stmt.close();
	}

	static void fetchOrderData() throws SQLException {
		String SQLtxt = "SELECT * FROM Orders";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);

		orderList.clear(); // Clear the list to avoid duplicates

		while (rs.next()) {
			Order order = new Order(rs.getString("OrderNumber"), rs.getDate("OrderDate"), rs.getString("EmployeeID"),
					rs.getString("ClientID"));

			orderList.add(order);
		}

		rs.close();
		stmt.close();
	}

	// Getters for each ObservableList
	public static ObservableList<Employee> getEmployeeData() {
		return empdataList;
	}

	public static ObservableList<Department> getDepartmentData() {
		return depdataList;
	}

	public static ObservableList<Company> getCompanyData() {
		return companydataList;
	}

	public static ObservableList<Product> getProductData() {
		return data;
	}

	public static ObservableList<Order> getOrderData() {
		return orderList;
	}

}