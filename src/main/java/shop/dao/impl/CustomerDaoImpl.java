package shop.dao.impl;

import shop.dao.CustomerDao;
import shop.entity.Customer;
import shop.entity.factory.BasicFactory;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.apache.log4j.Logger;

public class CustomerDaoImpl implements CustomerDao {
    private static final Logger LOG = Logger.getLogger(CustomerDaoImpl.class);
    private BasicFactory<Customer> basicFactory;
    @Autowired
    private DriverManagerDataSource dataSource;
    
    private CustomerDaoImpl() {}

    public CustomerDaoImpl(BasicFactory<Customer> basicFactory) {
        this.basicFactory = basicFactory;
    }

    @Override
    public List<Customer> getList(String filter) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            StringBuilder sb = new StringBuilder("SELECT * FROM `customers` ");
            if (!filter.equals("")) {
                sb.append("WHERE ").append(filter).append(" ");
            }
            sb.append("ORDER BY name ASC;");

            ResultSet rs = stmt.executeQuery(sb.toString());
            
            List<Customer> result = new ArrayList<Customer>();
            while (rs.next()) {
                Customer customer = basicFactory.factory(rs);
                result.add(customer);
            }
			return result;
		} catch (SQLException e) {
			return null;
		}
    }

    @Override
    public Customer findById(Long id) {
        try (
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `customers` WHERE `id`=?;");) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Customer customer = basicFactory.factory(rs);
                return customer;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }

    public Customer create(Customer entity) {
        return updateOrInsert(entity);
    }

    @Override
    public Customer updateOrInsert(Customer entity) {
        StringBuilder sb = new StringBuilder()
            .append("INSERT INTO `customers` (`id`, `ref`, `number`, `name`, `fullname`, `email`, `pass`, `deletionmark`) ")
            .append("VALUES (?, ?, ?, ?, ?, ?, ?, 'F') ")
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`number`=?, `name`=?, `fullname`=?, `email`=?, `pass`=?, `deletionmark`='F'");
            try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sb.toString());) {
                
                ps.setLong(1, entity.getId());
                ps.setString(2, entity.getRef());
                ps.setString(3, entity.getNumber());
                ps.setString(4, entity.getName());
                ps.setString(5, entity.getFullname());
                ps.setString(6, entity.getEmail());
                ps.setString(7, entity.getPass());
                
                ps.setString(8, entity.getNumber());
                ps.setString(9, entity.getName());
                ps.setString(10, entity.getFullname());
                ps.setString(11, entity.getEmail());
                ps.setString(12, entity.getPass());
                ps.executeUpdate();
    
                LOG.info("update customer " + entity);
                return entity;
            } catch (SQLException e) {
                LOG.error("something going wrong " + e);
                return entity;
            }
    }

    @Override
    public Customer delete(Customer entity) {
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `customers` WHERE `id`=?;");) {

            ps.setLong(1, new Long(entity.getId()));
            ps.execute();
            LOG.info("delete customer " + entity);

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
                Customer customer = basicFactory.factory(rs);
                return customer;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }
}