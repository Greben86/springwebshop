package shop.dao.impl;

import shop.dao.CustomerDao;
import shop.entity.Customer;
import shop.entity.factory.BasicFactory;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerDaoImpl implements CustomerDao {

    private static final Logger LOG = Logger.getLogger(CustomerDaoImpl.class);
    private BasicFactory<Customer> basicFactory;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CustomerDaoImpl() {
    }

    public CustomerDaoImpl(BasicFactory<Customer> basicFactory) {
        this.basicFactory = basicFactory;
    }

    @Override
    public List<Customer> getList(String filter) {
        return jdbcTemplate.query(
                "SELECT * FROM `customers` "
                + (filter.equals("") ? "" : "WHERE " + filter)
                + "ORDER BY name ASC;",
                (ResultSet rs, int rowNum) -> basicFactory.factory(rs));
    }

    @Override
    public Customer findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `customers` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                id);
    }

    @Override
    public Customer updateOrInsert(Customer entity) {
        jdbcTemplate.update(
                "INSERT INTO `customers` (`id`, `ref`, `number`, `name`, `fullname`, `email`, `pass`, `deletionmark`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'F') "
                + "ON DUPLICATE KEY UPDATE "
                + "`number`=?, `name`=?, `fullname`=?, `email`=?, `pass`=?, `deletionmark`='F'",
                entity.getId(),
                entity.getRef(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPass(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPass());
        LOG.info("update customer " + entity);
        return entity;
    }

    @Override
    public String updateList(List<Customer> list) {
        list.forEach(entity -> updateOrInsert(entity));
        return "Uploaded " + list.size() + " customers succesful";
    }

    @Override
    public Customer delete(Customer entity) {
        jdbcTemplate.update("DELETE FROM `customers` WHERE `id`=?;",
                entity.getId());
        return entity;
    }

    @Override
    public Customer findCustomerByNumber(String number) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `customers` WHERE `number`=?;",
                (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                number);
    }
}
