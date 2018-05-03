package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.RequestDao;
import shop.entity.RequestToAppend;
import shop.entity.factory.BasicFactory;

public class RequestDaoImpl implements RequestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BasicFactory<RequestToAppend> requestFactory;

    @Override
    public List<RequestToAppend> getList() {
        return jdbcTemplate.query(
                "SELECT * FROM `requests` ORDER BY `date_doc` DESC;",
                (ResultSet rs, int rowNum) -> requestFactory.factory(rs));
    }

    @Override
    public RequestToAppend findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `requests` WHERE `id`=?;", 
                (ResultSet rs, int rowNum) -> requestFactory.factory(rs),
                id);
    }

    @Override
    public void create(RequestToAppend entity) {
        jdbcTemplate.update("INSERT INTO `requests` (`date_doc`, `family`, `name`, `name2`, `fullname`, `email`, `phone`, `note`)"+
                " VALUES (?,?,?,?,?,?,?,?);", 
                entity.getDate(), 
                entity.getFamily(),
                entity.getName(),
                entity.getName2(),
                entity.getFullname(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getNote());
    }

    @Override
    public void update(RequestToAppend entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM `requests` WHERE `id`=?;", id);
    }

}
