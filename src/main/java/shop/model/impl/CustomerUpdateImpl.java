package shop.model.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import shop.model.CustomerUpdate;

public class CustomerUpdateImpl implements CustomerUpdate {
	@Autowired
	private DriverManagerDataSource dataSource;

	@Override
	public String delitionmarkforall() {
		try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute("UPDATE `customers` SET `deletionmark`='T';");

			return "Deletion mark for all is Ok";
		} catch (SQLException e) {
			return e.toString();
		}
	}

	@Override
	public String deletemarked() {
		try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute("DELETE FROM `customers` WHERE `deletionmark`='T';");

			return "Delete marked is Ok";
		} catch (SQLException e) {
			return e.toString();
		}
	}

	@Override
	public String update(String ref, String name, String number, String pass) {
		try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute("DELETE FROM `customers` WHERE `ref`='"+ref+"';");					
			stmt.executeUpdate(
				"INSERT INTO `customers` (`ref`, `number`, `name`, `pass`) "+
				"VALUES ('"+ref+"', '"+number+"', '"+name+"', '"+pass+"');");

			return name + " is Ok";
		} catch (SQLException e) {
			return e.toString();
		}
	}

	@Override
	public String deleteByRef(String ref) {
		try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute("DELETE FROM `customers` WHERE `ref`='"+ref+"';");

			return ref + " is Deleted";
		} catch (SQLException e) {
			return e.toString();
		}
	}
}