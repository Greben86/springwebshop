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
        List<RequestToAppend> result = jdbcTemplate.query(
                "SELECT * FROM `requests` ORDER BY date_doc DESC;",
                (ResultSet rs, int rowNum) -> requestFactory.factory(rs));
        return result;
    }

    @Override
    public RequestToAppend findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(RequestToAppend entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(RequestToAppend entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
