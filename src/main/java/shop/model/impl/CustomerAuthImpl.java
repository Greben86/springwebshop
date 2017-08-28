package shop.model.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import shop.model.CustomerAuth;

public class CustomerAuthImpl implements CustomerAuth {
    @Autowired
    private DriverManagerDataSource dataSource;

    @Override
    public Boolean checkPass(String number, String pass) {
        try (
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement()) {

            ResultSet result = stmt.executeQuery("SELECT `pass` FROM `customers` WHERE `number`='"+number+"'");

            if (result.next()) {
                return pass.equalsIgnoreCase(result.getString("pass"));
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}