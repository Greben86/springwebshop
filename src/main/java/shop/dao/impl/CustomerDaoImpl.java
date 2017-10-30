package shop.dao.impl;

import shop.dao.CustomerDao;
import shop.entity.Customer;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CustomerDaoImpl implements CustomerDao {
    @Autowired
	private DriverManagerDataSource dataSource;

    @Override
    public List<Customer> getList(String filter) {
        List<Customer> result = new ArrayList<Customer>();
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `customers` " + (filter.equals("") ? "" : "WHERE "+filter));
            
            while (rs.next()) {
                Customer e = new Customer();
                e.setRef(rs.getString("ref"));
                e.setNumber(rs.getString("number"));
                e.setName(rs.getString("name"));
                e.setPass(rs.getString("pass"));
                result.add(e);
            }

			return result;
		} catch (SQLException e) {
			return result;
		}
    }

    @Override
    public Customer findById(Long id) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `customers` WHERE `id`=%d;", id));
            
            if (rs.next()) {
                Customer e = new Customer();
                e.setRef(rs.getString("ref"));
                e.setNumber(rs.getString("number"));
                e.setName(rs.getString("name"));
                e.setPass(rs.getString("pass"));
    
                return e;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }

    @Override
    public Customer findByRef(String ref) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `customers` WHERE `ref`='"+ref+"'");
            
            if (rs.next()) {
                Customer e = new Customer();
                e.setRef(rs.getString("ref"));
                e.setNumber(rs.getString("number"));
                e.setName(rs.getString("name"));
                e.setPass(rs.getString("pass"));
    
                return e;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }

    public Customer create(Customer entity) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.executeUpdate(
				"INSERT INTO `customers` (`ref`, `number`, `name`, `pass`, `deletionmark`) "+
				"VALUES ('"+entity.getRef()+"', '"+entity.getNumber()+"', '"+entity.getName()+"', '"+entity.getPass()+"', '"+(entity.getDeletionmark()?"T":"F")+"');");

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }

    @Override
    public Customer updateOrInsert(Customer entity) {
        return create(delete(entity));
    }

    @Override
    public Customer delete(Customer entity) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute("DELETE FROM `customers` WHERE `ref`='"+entity.getRef()+"';");

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }

    @Override
    public Customer findCustomerByNumber(String number) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `customers` WHERE `number`='"+number+"'");
            
            if (rs.next()) {
                Customer e = new Customer();
                e.setRef(rs.getString("ref"));
                e.setNumber(rs.getString("number"));
                e.setName(rs.getString("name"));
                e.setPass(rs.getString("pass"));
    
                return e;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }
}