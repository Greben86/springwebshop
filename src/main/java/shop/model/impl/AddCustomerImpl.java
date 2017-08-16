package shop.model.impl;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import shop.model.AddCustomer;

public class AddCustomerImpl implements AddCustomer {

	private static final String url = "jdbc:mysql://localhost:3306/webshop";
    private static final String user = "root";
    private static final String password = "123";

	@Override
	public String add() {
		Connection connection;
		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(url, user, password);
			stmt = connection.createStatement();

			String query = "INSERT INTO `customers` (`id`, `in`, `name`, `pass`)  VALUES (1, '123', 'Kathy Sieara', '1');";

			stmt.executeUpdate(query);

			return "Connection is Ok!";
		} catch (ClassNotFoundException | SQLException e) {
			return e.toString();
		}		
	}
}