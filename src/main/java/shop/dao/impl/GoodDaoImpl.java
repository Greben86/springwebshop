package shop.dao.impl;

import shop.dao.GoodDao;
import shop.entity.Good;
import shop.entity.factory.BasicFactory;

import java.util.List;
import java.util.ArrayList;
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

    private GoodDaoImpl() {}
    
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
            sb.append("ORDER BY name ASC;");

            ResultSet rs = stmt.executeQuery(sb.toString());
            
            List<Good> result = new ArrayList<Good>();
            Good good;
            while (rs.next()) {
                good = basicFactory.factory(rs);
                if (rs.getString("folder").equals("F"))
                {
                    result.add(good);
                } else
                if (hasChild(good.getId()))
                {
                    result.add(good);
                }                    
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
        StringBuilder sb = new StringBuilder()
            .append("INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`, `deletionmark`) ")
            .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'F') ")
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?, `deletionmark`='F'");
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());) {
            
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
        StringBuilder sb = new StringBuilder()
            .append("INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`) ")
            .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ")
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?");
        try (
			Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());) {
            
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

			return "Uploaded " + list.size() + " goods succesfull";
		} catch (SQLException e) {
            LOG.error("something going wrong " + e);
			return e.getMessage();
		}
    }
    
    @Override
    public Good delete(Good entity) {
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `goods` WHERE `id`=?;");) {

            ps.setLong(1, new Long(entity.getId()));
            ps.execute();
            LOG.info("delete good " + entity);

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }

    private Boolean findChild(ResultSet rs, long owner) throws SQLException {
        List<Long> buffer = new ArrayList<Long>();
        rs.first();
        while (rs.next()) {
            if (owner==rs.getLong("owner")) {
                if (rs.getString("folder").equals("F")) {
                    return true;
                } else {
                    buffer.add(new Long(rs.getLong("id")));
                }                
            }
        }
        for (Long id : buffer)
            if (findChild(rs, id))
                return true;
        return false;
    }

    public Boolean hasChild(long id) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT `id`, `owner`, `folder` FROM `goods` ORDER BY `id` ASC");

			return findChild(rs, id);
		} catch (SQLException e) {
			return false;
		}
    }
}