package shop.dao.impl;

import shop.dao.GoodDao;
import shop.entity.Good;

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
    @Autowired
    private DriverManagerDataSource dataSource;

    @Override
    public List<Good> getList(String filter) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods`" + (filter.equals("") ? "" : " WHERE "+filter) + " ORDER BY folder DESC;");
            
            List<Good> result = new ArrayList<Good>();
            while (rs.next()) {
                result.add(new Good(rs));
            }
			return result;
		} catch (SQLException e) {
			return null;
		}
    }
    
    @Override
    public Good findByRef(String ref) {
        // try (
		// 	Connection connection = dataSource.getConnection();
		// 	Statement stmt = connection.createStatement()) {

        //     ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `goods` WHERE `ref`='%s'", ref));
            
        //     if (rs.next()) {    
        //         return new Good(rs);
        //     } else {
        //         return null;
        //     }            
		// } catch (SQLException e) {
			return null;
		// }
    }
    
    @Override
    public Good create(Good entity) {
        // try (
		// 	Connection connection = dataSource.getConnection();
		// 	Statement stmt = connection.createStatement()) {

        //     StringBuilder buff = new StringBuilder()
        //         .append("INSERT INTO `goods` (")
        //         .append("`id`, `owner`, `folder`, `ref`, `name`, `description`, `articul`, `filename`, `price`, `exist`, `deletionmark`")
        //         .append(") VALUES (")
        //         .append(entity.getId())
        //         .append(", ").append(entity.getOwner())
        //         .append(", '").append(entity.getFolder() ? "T" : "F").append("'")
        //         .append(", '").append(entity.getRef()).append("'")
        //         .append(", '").append(entity.getName()).append("'")
        //         .append(", '").append(entity.getDescription()).append("'")
        //         .append(", '").append(entity.getArticul()).append("'")
        //         .append(", '").append(entity.getFilename()).append("'")
        //         .append(", ").append(entity.getPrice())
        //         .append(", '").append(entity.getExist() ? "T" : "F").append("'")
        //         .append(", '").append(entity.getDeletionmark() ? "T" : "F").append("'")
        //         .append(") ON DUPLICATE KEY UPDATE ")
        //         .append(" `id`=").append(entity.getId())
        //         .append(", `owner`=").append(entity.getOwner())
        //         .append(", `folder`='").append(entity.getFolder() ? "T" : "F").append("'")
        //         .append(", `ref`='").append(entity.getRef()).append("'")
        //         .append(", `name`='").append(entity.getName()).append("'")
        //         .append(", `description`='").append(entity.getDescription()).append("'")
        //         .append(", `articul`='").append(entity.getArticul()).append("'")
        //         .append(", `filename`='").append(entity.getFilename()).append("'")
        //         .append(", `price`=").append(entity.getPrice())
        //         .append(", `exist`='").append(entity.getExist() ? "T" : "F").append("'")
        //         .append(", `deletionmark`='").append(entity.getDeletionmark() ? "T" : "F").append("'");
		// 	stmt.executeUpdate(buff.toString());

		// 	return entity;
		// } catch (SQLException e) {
			return entity;
		// }
    }
    
    @Override
    public Good update(Good entity) {
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

            LOG.info("update good " + entity.getName());
            return entity;
        } catch (SQLException e) {
            LOG.error("something going wrong " + e);
            return entity;
        }
    }

    @Override
    public String updateList(List<Good> list) {
        StringBuilder sb = new StringBuilder()
            .append("INSERT INTO `goods` (`id`, `owner`, `folder`, `name`, `description`, `article`, `price`, `instock`, `deletionmark`) ")
            .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'F') ")
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`owner`=?, `name`=?, `description`=?, `article`=?, `price`=?, `instock`=?, `deletionmark`='F'");
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
                
                LOG.info("update good " + entity.getName());
            }
            connection.commit();

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
			Statement stmt = connection.createStatement()) {

			stmt.execute(String.format("DELETE FROM `goods` WHERE `id`='%d';", entity.getId()));

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }

    @Override
    public Good getById(long id) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `goods` WHERE `id`=%d;", id));
            
            if (rs.next()) {    
                return new Good(rs);
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }

    @Override
    public Good findGoodByArticul(String articul) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `goods` WHERE `article`='%s'", articul));
            
            if (rs.next()) {    
                return new Good(rs);
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }
}