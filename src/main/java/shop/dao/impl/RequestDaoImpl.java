package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.RequestDao;
import shop.entity.RequestToAppend;
import shop.entity.factory.impl.RequestFactoryImpl;

public class RequestDaoImpl implements RequestDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RequestToAppend> getList(String filter) {
        RequestFactoryImpl factory = new RequestFactoryImpl();
        List<RequestToAppend> result = jdbcTemplate.query(
                "SELECT * FROM statements ORDER BY date_doc DESC;",
                (ResultSet rs, int rowNum) -> factory.factory(rs));
        return result;
    }

    @Override
    public RequestToAppend findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestToAppend updateOrInsert(RequestToAppend entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestToAppend delete(RequestToAppend entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
