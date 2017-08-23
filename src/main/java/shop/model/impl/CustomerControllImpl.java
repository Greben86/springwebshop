package shop.model.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import shop.model.CustomerControll;

public class CustomerControllImpl implements CustomerControll {
	private static final String url = "jdbc:mysql://localhost:3306/webshop";
	private Properties properties;

	private CustomerControllImpl() {
		properties = new Properties();
		properties.setProperty("user","root");
		properties.setProperty("password","123");
		properties.setProperty("useUnicode","true");
		properties.setProperty("characterEncoding","cp1251");
	}

	static public CustomerControllImpl newInstance(){
		return new CustomerControllImpl();
	}

	@Override
	public String delitionmarkforall() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try (
				Connection connection = DriverManager.getConnection(url, properties);
				Statement stmt = connection.createStatement()) {

					stmt.execute("UPDATE `customers` SET `deletionmark`='T';");

					return "Deletion mark for all is Ok";
			} catch (SQLException e) {
				return e.toString();
			}
		} catch (ClassNotFoundException e) {
			return e.toString();
		}
	}

	@Override
	public String deletemarked() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try (
				Connection connection = DriverManager.getConnection(url, properties);
				Statement stmt = connection.createStatement()) {

					stmt.execute("DELETE FROM `customers` WHERE `deletionmark`='T';");

					return "Delete marked is Ok";
			} catch (SQLException e) {
				return e.toString();
			}
		} catch (ClassNotFoundException e) {
			return e.toString();
		}
	}

	@Override
	public String update(String ref, String name, String number, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try (
				Connection connection = DriverManager.getConnection(url, properties);
				Statement stmt = connection.createStatement()) {

					stmt.execute("DELETE FROM `customers` WHERE `ref`='"+ref+"';");					
					stmt.executeUpdate(
						"INSERT INTO `customers` (`ref`, `number`, `name`, `pass`) "+
						"VALUES ('"+ref+"', '"+number+"', '"+name+"', '"+pass+"');");

					return name + " is Ok";
			} catch (SQLException e) {
				return e.toString();
			}
		} catch (ClassNotFoundException e) {
			return e.toString();
		}
	}
}