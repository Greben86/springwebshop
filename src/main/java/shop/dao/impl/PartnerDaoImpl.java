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
        return jdbcTemplate.query(
                "SELECT * FROM `partners` ORDER BY `ordr` ASC;",
                (ResultSet rs, int rowNum) -> partnerFactory.factory(rs));
    }

    @Override
    public Partner findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `partners` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> partnerFactory.factory(rs),
                id);
    }

    @Override
    public void create(Partner entity) {
        jdbcTemplate.update(
                "INSERT INTO `partners` (`note`, `link`, `discount`, `file`) VALUES (?, ?, ?, ?);",
                entity.getNote(),
                entity.getLink(),
                entity.getDiscount(), 
                entity.getFilename());
    }

    @Override
    public void update(Partner entity) {
        jdbcTemplate.update(
                "UPDATE `partners` SET `note`=?, `link`=?, `discount`=?, `file`=?, `ordr`=? "
                + "WHERE `id`=?;",
                entity.getNote(),
                entity.getLink(),
                entity.getDiscount(),
                entity.getFilename(),
                entity.getOrdr(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM `partners` WHERE `id`=?;", id);
    }

}
