package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.PromoDao;
import shop.entity.Promo;
import shop.entity.factory.BasicFactory;

public class PromoDaoImpl implements PromoDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired 
    private BasicFactory<Promo> promoFactory;

    @Override
    public List<Promo> getList() {
        List<Promo> result = jdbcTemplate.query(
                "SELECT * FROM `promos`",
                (ResultSet rs, int rowNum) -> promoFactory.factory(rs));
        return result;
    }

    @Override
    public Promo findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Promo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Promo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
