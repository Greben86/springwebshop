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
                "SELECT * FROM `promo_list` ORDER BY `name` ASC;",
                (ResultSet rs, int rowNum) -> promoFactory.factory(rs));
    }

    @Override
    public Promo findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `promo_list` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> promoFactory.factory(rs),
                id);
    }

    @Override
    public void create(Promo entity) {
        jdbcTemplate.update(
                "INSERT INTO `promo_list` (`name`, `filename`) VALUES (?, ?);", 
                entity.getName(),
                entity.getFilename());
    }

    @Override
    public void update(Promo entity) {
        jdbcTemplate.update(
                "UPDATE `promo_list` SET `name`=?, `filename`=? WHERE `id`=?;", 
                entity.getName(),
                entity.getFilename());
    }

    @Override
    public void delete(Promo entity) {
        jdbcTemplate.update("DELETE FROM `promo_list` WHERE `id`=?;", entity.getId());
    }

}
