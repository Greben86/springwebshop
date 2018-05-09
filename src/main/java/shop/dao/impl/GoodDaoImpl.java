package shop.dao.impl;

import shop.dao.GoodDao;
import shop.entity.Good;
import shop.entity.factory.BasicFactory;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.apache.log4j.Logger;

public class GoodDaoImpl implements GoodDao {

    private static final Logger LOG = Logger.getLogger(GoodDaoImpl.class);
    private BasicFactory<Good> basicFactory;
    @Autowired
    private DriverManagerDataSource dataSource;

    private GoodDaoImpl() {
    }

    public GoodDaoImpl(BasicFactory<Good> basicFactory) {
        this.basicFactory = basicFactory;
    }

    @Override
    public List<Good> getList(String filter) {
        try (
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement()) {

            StringBuilder sb = new StringBuilder("SELECT * FROM `goods` ");
            if (!filter.equals("")) {
                sb.append("WHERE ").append(filter).append(" ");
            }
            sb.append("ORDER BY `name` ASC;");

            ResultSet rs = stmt.executeQuery(sb.toString());

            List<Good> result = new LinkedList<Good>();
            while (rs.next()) {
                result.add(basicFactory.factory(rs));
            }
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Good findById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM `goods` WHERE `id`=?;");) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return basicFactory.factory(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Good updateOrInsert(Good entity) {
        String sql = "INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`, `deletionmark`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'F') "
                + "ON DUPLICATE KEY UPDATE "
                + "`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?, `deletionmark`='F';";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setLong(1, entity.getId());
            ps.setLong(2, entity.getOwner());
            ps.setString(3, entity.getFolder() ? "T" : "F");
            ps.setString(4, entity.getName());
            ps.setString(5, entity.getDescription());
            ps.setString(6, entity.getArticle());
            ps.setFloat(7, entity.getPrice());
            ps.setFloat(8, entity.getInstock());

            ps.setLong(9, entity.getOwner());
            ps.setString(10, entity.getName());
            ps.setString(11, entity.getDescription());
            ps.setString(12, entity.getArticle());
            ps.setFloat(13, entity.getPrice());
            ps.setFloat(14, entity.getInstock());
            ps.executeUpdate();

            LOG.info("update good " + entity);
            return entity;
        } catch (SQLException e) {
            LOG.error("something going wrong " + e);
            return entity;
        }
    }

    @Override
    public String updateList(List<Good> list) {
        String sql = "INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`, `deletionmark`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'F') "
                + "ON DUPLICATE KEY UPDATE "
                + "`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?, `deletionmark`='F';";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);) {

            connection.setAutoCommit(false);
            for (Good entity : list) {
                ps.setLong(1, entity.getId());
                ps.setLong(2, entity.getOwner());
                ps.setString(3, entity.getFolder() ? "T" : "F");
                ps.setString(4, entity.getName());
                ps.setString(5, entity.getDescription());
                ps.setString(6, entity.getArticle());
                ps.setFloat(7, entity.getPrice());
                ps.setFloat(8, entity.getInstock());

                ps.setLong(9, entity.getOwner());
                ps.setString(10, entity.getName());
                ps.setString(11, entity.getDescription());
                ps.setString(12, entity.getArticle());
                ps.setFloat(13, entity.getPrice());
                ps.setFloat(14, entity.getInstock());

                ps.executeUpdate();

                LOG.info("update good " + entity);
            }
            connection.commit();
            LOG.info("commit for goods");

            return "Uploaded " + list.size() + " goods succesful";
        } catch (SQLException e) {
            LOG.error("something going wrong " + e);
            return e.getMessage();
        }
    }

    @Override
    public void delete(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM `goods` WHERE `id`=?;");) {

            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            
        }
    }

    @Override
    public void create(Good entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Good entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Good> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
