package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.PartnerDao;
import shop.entity.Partner;
import shop.entity.factory.BasicFactory;

public class PartnerDaoImpl implements PartnerDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired 
    private BasicFactory<Partner> partnerFactory;

    @Override
    public List<Partner> getList() {
        List<Partner> result = jdbcTemplate.query(
                "SELECT * FROM `partners`",
                (ResultSet rs, int rowNum) -> partnerFactory.factory(rs));
        return result;
    }

    @Override
    public Partner findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Partner entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Partner entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
