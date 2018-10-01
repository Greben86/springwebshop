package shop.dao.impl;

import shop.dao.CustomerDao;
import shop.entity.Customer;
import shop.entity.factory.BasicFactory;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
    public List<Customer> getList() {
        List<Customer> result = jdbcTemplate.query(
                "SELECT * FROM `customers` ORDER BY `name` ASC;",
                (ResultSet rs, int rowNum) -> basicFactory.factory(rs));
        result.stream().forEach(item -> readPayDetail(item));
        return result;
    }

    @Override
    public Customer findById(Long id) {
        Customer result = jdbcTemplate.queryForObject(
                "SELECT * FROM `customers` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                id);
        readPayDetail(result);
        return result;
    }

    private void readPayDetail(Customer customer) {
        customer.setPayList1(
                jdbcTemplate.query("SELECT * FROM `pay_detail` WHERE `program_id`=1 and `customer_id`=?", 
                        (ResultSet rs, int rowNum) -> rs.getDate("date_pay"), 
                        customer.getId()));
        customer.setPayList3(
                jdbcTemplate.query("SELECT * FROM `pay_detail` WHERE `program_id`=3 and `customer_id`=?", 
                        (ResultSet rs, int rowNum) -> rs.getDate("date_pay"), 
                        customer.getId()));
        customer.setPayList4(
                jdbcTemplate.query("SELECT * FROM `pay_detail` WHERE `program_id`=4 and `customer_id`=?", 
                        (ResultSet rs, int rowNum) -> rs.getDate("date_pay"), 
                        customer.getId()));
    }

    @Override
    public void create(Customer entity) {
        jdbcTemplate.update(
                "INSERT INTO `customers` (`ref`, `number`, `name`, `fullname`, `email`, `pass`, `ballance`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);",
                entity.getRef(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPass(),
                entity.getBallance());
        try {
            saveDetail(findByEmail(entity.getEmail()));
        } catch (EmptyResultDataAccessException e) {

        }
        LOG.info("create customer " + entity);
    }

    @Override
    public void update(Customer entity) {
        jdbcTemplate.update(
                "UPDATE `customers` SET `ref`=?, `number`=?, `name`=?, `fullname`=?, `email`=?, `ballance`=? "
                + "WHERE `id_local`=?;",
                entity.getRef(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getBallance(),
                entity.getId());
        saveDetail(entity);
        LOG.info("update customer " + entity);
    }

    private void saveDetail(Customer customer) {
        if (customer != null) {
            jdbcTemplate.update(
                    "DELETE FROM `pay_detail` WHERE `customer_id`=?",
                    customer.getId());
            if (customer.getPayList1() != null) {
                customer.getPayList1().stream().forEach(date -> {
                    jdbcTemplate.update(
                            "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 1, ?)",
                            customer.getId(),
                            date
                    );
                });
            }
            if (customer.getPayList3() != null) {
                customer.getPayList3().stream().forEach(date -> {
                    jdbcTemplate.update(
                            "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 3, ?)",
                            customer.getId(),
                            date
                    );
                });
            }
            if (customer.getPayList4() != null) {
                customer.getPayList4().stream().forEach(date -> {
                    jdbcTemplate.update(
                            "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 4, ?)",
                            customer.getId(),
                            date
                    );
                });
            }
        }
    }
    
    public Customer updateOrInsert(Customer entity) {
        jdbcTemplate.update(
                "INSERT INTO `customers` (`id`, `ref`, `number`, `name`, `fullname`, `email`, `pass`, `ballance`, `deletionmark`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'F') "
                + "ON DUPLICATE KEY UPDATE "
                + "`number`=?, `name`=?, `fullname`=?, `email`=?, `pass`=?, `ballance`=?, `deletionmark`='F'",
                entity.getId(),
                entity.getRef(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPass(),
                entity.getBallance(),
                entity.getNumber(),
                entity.getName(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPass(),
                entity.getBallance());
        LOG.info("update customer " + entity);
        return entity;
    }

    @Override
    public String updateList(List<Customer> list) {
        list.forEach(entity -> updateOrInsert(entity));
        return "Uploaded " + list.size() + " customers succesful";
    }

    @Override
    public void delete(Customer entity) {
        jdbcTemplate.update("DELETE FROM `customers` WHERE `id_ext`=?;", entity.getId());
    }

    @Override
    public Customer findByNumber(String number) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM `customers` WHERE `number`=?;",
                    (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                    number);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM `customers` WHERE `email`=?;",
                    (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                    email);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer findByRef(String ref) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM `customers` WHERE `ref`=?;",
                    (ResultSet rs, int rowNum) -> basicFactory.factory(rs),
                    ref);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void updatePass(Customer customer, String pass) {
        jdbcTemplate.update(
                "UPDATE `customers` SET `pass`=? WHERE `id`=?;",
                pass,
                customer.getId());
        LOG.info("update pass from customer " + customer);
    }

}
