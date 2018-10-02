package shop.dao.impl;

import shop.dao.GoodDao;
import shop.entity.Good;
import shop.entity.factory.BasicFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class GoodDaoImpl implements GoodDao {

    private BasicFactory<Good> basicFactory;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private GoodDaoImpl() {
    }

    public GoodDaoImpl(BasicFactory<Good> basicFactory) {
        this.basicFactory = basicFactory;
    }
    
    @Override
    public List<Good> getList() {
        return getList("");
    }

    @Override
    public List<Good> getList(String filter) {
        return jdbcTemplate.query("SELECT * FROM `goods` " 
                + (filter.isEmpty() ? "" : "WHERE " + filter + " ") 
                + "ORDER BY `name` ASC;", 
                (rs, rowNum) -> basicFactory.factory(rs));
    }

    @Override
    public Good findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM `goods` WHERE `id`=?;", 
                (rs, rowNum) -> basicFactory.factory(rs), 
                id);
    }

    @Override
    public void updateOrInsert(Good entity) {
        jdbcTemplate.update(
                "INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?;", 
                entity.getId(),
                entity.getOwner(),
                entity.getFolder() ? "T" : "F",
                entity.getName(),
                entity.getDescription(),
                entity.getArticle(),
                entity.getPrice(),
                entity.getInstock(),
                entity.getOwner(),
                entity.getName(),
                entity.getDescription(),
                entity.getArticle(),
                entity.getPrice(),
                entity.getInstock());
    }

    @Override
    public void delete(Good entity) {
        jdbcTemplate.update("DELETE FROM `goods` WHERE `id`=?;", entity.getId());
    }

    @Deprecated
    @Override
    public void create(Good entity) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Deprecated
    @Override
    public void update(Good entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
