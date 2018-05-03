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
        return jdbcTemplate.query(
                "SELECT * FROM `promos` ORDER BY `name` ASC;",
                (ResultSet rs, int rowNum) -> promoFactory.factory(rs));
    }

    @Override
    public Promo findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `promos` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> promoFactory.factory(rs),
                id);
    }

    @Override
    public void create(Promo entity) {
        jdbcTemplate.update(
                "INSERT INTO `promos` (`name`) VALUES (?);", 
                entity.getName());
    }

    @Override
    public void update(Promo entity) {
        jdbcTemplate.update(
                "UPDATE `promos` SET `name`=? WHERE `id`=?;", 
                entity.getName());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM `promos` WHERE `id`=?;", id);
    }

}
