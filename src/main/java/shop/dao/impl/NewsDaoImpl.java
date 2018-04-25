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
    public List<News> getList(String filter) {
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
                "INSERT INTO `news` (`title`, `body`, `date`, `enabled`) "
                + "VALUES (?, ?, ?, ?);",
                entity.getTitle(),
                entity.getBody(),
                entity.getDate(),
                entity.getEnabled());
    }

    @Override
    public void update(News entity) {
        jdbcTemplate.update(
                "INSERT INTO `news` (`title`, `body`, `date`, `enabled`) "
                + "VALUES (?, ?, ?, ?);",
                entity.getTitle(),
                entity.getBody(),
                entity.getDate(),
                entity.getEnabled());
    }
    
    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM `news` WHERE `id`=?;", id);
    }

}
