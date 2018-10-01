package shop.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shop.dao.NewsDao;
import shop.entity.News;
import shop.entity.factory.BasicFactory;

public class NewsDaoImpl implements NewsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BasicFactory<News> newsFactory;

    @Override
    public List<News> getList() {
        return jdbcTemplate.query(
                "SELECT * FROM `news` ORDER BY `date` DESC;",
                (ResultSet rs, int rowNum) -> newsFactory.factory(rs));
    }

    @Override
    public News findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `news` WHERE `id`=?;",
                (ResultSet rs, int rowNum) -> newsFactory.factory(rs),
                id);
    }

    @Override
    public void create(News entity) {
        jdbcTemplate.update(
                "INSERT INTO `news` (`title`, `body`, `filename`, `date`, `enabled`) "
                + "VALUES (?, ?, ?, current_timestamp, ?);",
                entity.getTitle(),
                entity.getBody(),
                entity.getFilename(),
                entity.getEnabled());
    }

    @Override
    public void update(News entity) {
        jdbcTemplate.update(
                "UPDATE `news` SET `title`=?, `body`=?, `filename`=?, `enabled`=? "
                + "WHERE `id`=?;",
                entity.getTitle(),
                entity.getBody(),
                entity.getFilename(),
                entity.getEnabled(),
                entity.getId());
    }

    @Override
    public void delete(News entity) {
        jdbcTemplate.update("DELETE FROM `news` WHERE `id`=?;", entity.getId());
    }

}
