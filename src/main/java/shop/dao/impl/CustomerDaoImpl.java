package shop.dao.impl;

import shop.dao.CustomerDao;
import shop.entity.Customer;
import shop.entity.factory.BasicFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import static java.util.Optional.ofNullable;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
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
        return jdbcTemplate.query(
                "SELECT * FROM `customers` ORDER BY `name` ASC;",
                (rs, rowNum) -> basicFactory.factory(rs));
    }

    @Override
    public Customer findById(Long id) {
        Customer result = jdbcTemplate.queryForObject(
                "SELECT * FROM `customers` WHERE `id`=?;",
                (rs, rowNum) -> basicFactory.factory(rs),
                id);
        readDetail(result);
        return result;
    }

    @Override
    public void readDetail(Customer customer) {
        customer.setPayList1(
                jdbcTemplate.query(
                        "SELECT * FROM `pay_detail` WHERE `program_id`=1 and `customer_id`=?",
                        (rs, rowNum) -> rs.getDate("date_pay"),
                        customer.getId()));
        customer.setPayList3(
                jdbcTemplate.query(
                        "SELECT * FROM `pay_detail` WHERE `program_id`=3 and `customer_id`=?",
                        (rs, rowNum) -> rs.getDate("date_pay"),
                        customer.getId()));
        customer.setPayList4(
                jdbcTemplate.query(
                        "SELECT * FROM `pay_detail` WHERE `program_id`=4 and `customer_id`=?",
                        (rs, rowNum) -> rs.getDate("date_pay"),
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

    @Override
    public void saveDetail(Customer customer) {
        if (customer != null) {
            jdbcTemplate.update(
                    "DELETE FROM `pay_detail` WHERE `customer_id`=?",
                    customer.getId());
            ofNullable(customer.getPayList1())
                    .ifPresent(list -> list.stream()
                    .forEach(date -> jdbcTemplate.update(
                    "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 1, ?)",
                    customer.getId(),
                    date
            )));
            ofNullable(customer.getPayList3())
                    .ifPresent(list -> list.stream()
                    .forEach(date -> jdbcTemplate.update(
                    "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 3, ?)",
                    customer.getId(),
                    date
            )));
            ofNullable(customer.getPayList4())
                    .ifPresent(list -> list.stream()
                    .forEach(date -> jdbcTemplate.update(
                    "INSERT INTO `pay_detail` (`customer_id`, `program_id`, `date_pay`) VALUES (?, 4, ?)",
                    customer.getId(),
                    date
            )));
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
    public void clearDetail(Customer customer) {
        
    }

    @Override
    public Customer findByNumber(String number) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM `customers` WHERE `number`=?;",
                    (rs, rowNum) -> basicFactory.factory(rs),
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
                    (rs, rowNum) -> basicFactory.factory(rs),
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
                    (rs, rowNum) -> basicFactory.factory(rs),
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
