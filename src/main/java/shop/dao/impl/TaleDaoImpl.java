package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.TaleDao;
import shop.entity.Tale;
import shop.entity.factory.BasicFactory;

public class TaleDaoImpl implements TaleDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BasicFactory<Tale> taleFactory;

    @Override
    public List<Tale> getList() {
        return jdbcTemplate.query(
                "SELECT * FROM `tale_list` ORDER BY `date` DESC;",
                (ResultSet rs, int rowNum) -> taleFactory.factory(rs));
    }

    @Override
    public Tale findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `tale_list` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> taleFactory.factory(rs),
                id);
    }

    @Override
    public void create(Tale entity) {
        jdbcTemplate.update(
                "INSERT INTO `tale_list` (`title`, `body`, `filename`, `date`, `enabled`) "
                + "VALUES (?, ?, ?, current_timestamp, ?);",
                entity.getTitle(),
                entity.getBody(),
                entity.getFilename(),
                entity.getEnabled());
    }

    @Override
    public void update(Tale entity) {
        jdbcTemplate.update(
                "UPDATE `tale_list` SET `title`=?, `body`=?, `filename`=?, `enabled`=? "
                + "WHERE `id`=?;",
                entity.getTitle(),
                entity.getBody(),
                entity.getFilename(),
                entity.getEnabled(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM `tale_list` WHERE `id`=?;", id);
    }
    
}
