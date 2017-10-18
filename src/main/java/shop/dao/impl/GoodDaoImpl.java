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
    
    private String buildUpdateSql(Good good) {
        StringBuilder buff = new StringBuilder()
            .append("INSERT INTO `goods` (")
            .append("`id`, `owner`, `folder`, `ref`, `name`, `description`, `articul`, `filename`, `price`, `exist`, `deletionmark`")
            .append(") VALUES (")
            .append(good.getId())
            .append(", ").append(good.getOwner())
            .append(", '").append(good.getFolder() ? "T" : "F").append("'")
            .append(", '").append(good.getRef()).append("'")
            .append(", '").append(good.getName()).append("'")
            .append(", '").append(good.getDescription()).append("'")
            .append(", '").append(good.getArticul()).append("'")
            .append(", '").append(good.getFilename()).append("'")
            .append(", ").append(good.getPrice())
            .append(", '").append(good.getExist() ? "T" : "F").append("'")
            .append(", '").append(good.getDeletionmark() ? "T" : "F").append("'")
            .append(") ON DUPLICATE KEY UPDATE ")
            .append(" `id`=").append(good.getId())
            .append(", `owner`=").append(good.getOwner())
            .append(", `folder`='").append(good.getFolder() ? "T" : "F").append("'")
            .append(", `ref`='").append(good.getRef()).append("'")
            .append(", `name`='").append(good.getName()).append("'")
            .append(", `description`='").append(good.getDescription()).append("'")
            .append(", `articul`='").append(good.getArticul()).append("'")
            .append(", `filename`='").append(good.getFilename()).append("'")
            .append(", `price`=").append(good.getPrice())
            .append(", `exist`='").append(good.getExist() ? "T" : "F").append("'")
            .append(", `deletionmark`='").append(good.getDeletionmark() ? "T" : "F").append("'");
        return buff.toString();
    }

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
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `goods` WHERE `ref`='%s'", ref));
            
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
    public Good create(Good entity) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            StringBuilder buff = new StringBuilder()
                .append("INSERT INTO `goods` (")
                .append("`id`, `owner`, `folder`, `ref`, `name`, `description`, `articul`, `filename`, `price`, `exist`, `deletionmark`")
                .append(") VALUES (")
                .append(entity.getId())
                .append(", ").append(entity.getOwner())
                .append(", '").append(entity.getFolder() ? "T" : "F").append("'")
                .append(", '").append(entity.getRef()).append("'")
                .append(", '").append(entity.getName()).append("'")
                .append(", '").append(entity.getDescription()).append("'")
                .append(", '").append(entity.getArticul()).append("'")
                .append(", '").append(entity.getFilename()).append("'")
                .append(", ").append(entity.getPrice())
                .append(", '").append(entity.getExist() ? "T" : "F").append("'")
                .append(", '").append(entity.getDeletionmark() ? "T" : "F").append("'")
                .append(") ON DUPLICATE KEY UPDATE ")
                .append(" `id`=").append(entity.getId())
                .append(", `owner`=").append(entity.getOwner())
                .append(", `folder`='").append(entity.getFolder() ? "T" : "F").append("'")
                .append(", `ref`='").append(entity.getRef()).append("'")
                .append(", `name`='").append(entity.getName()).append("'")
                .append(", `description`='").append(entity.getDescription()).append("'")
                .append(", `articul`='").append(entity.getArticul()).append("'")
                .append(", `filename`='").append(entity.getFilename()).append("'")
                .append(", `price`=").append(entity.getPrice())
                .append(", `exist`='").append(entity.getExist() ? "T" : "F").append("'")
                .append(", `deletionmark`='").append(entity.getDeletionmark() ? "T" : "F").append("'");
			stmt.executeUpdate(buff.toString());

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }
    
    @Override
    public Good update(Good entity) {
        return create(delete(entity));
    }
    
    @Override
    public Good delete(Good entity) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

			stmt.execute(String.format("DELETE FROM `goods` WHERE `ref`='%s';", entity.getRef()));

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

            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `goods` WHERE `articul`='%s'", articul));
            
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
    public String updateList(List<Good> list) {
        StringBuilder sb = new StringBuilder()
            .append("INSERT INTO `goods` (`id`, `owner`, `folder`, `ref`, `name`, `description`, `articul`, `filename`, `price`, `exist`, `deletionmark`) ")
            .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'F')")
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`id`=?, `owner`=?, `folder`=?, `name`=?, `description`=?, `articul`=?, `filename`=?, `price`=?, `exist`=?, `deletionmark`='F'");
        try (
			Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());) {
            
            connection.setAutoCommit(false);
            for (int i=0; i<list.size(); i++) {
                ps.setLong(1, list.get(i).getId());
                ps.setLong(2, list.get(i).getOwner());
                ps.setString(3, list.get(i).getFolder() ? "T" : "F");
                ps.setString(4, list.get(i).getRef());
                ps.setString(5, list.get(i).getName());
                ps.setString(6, list.get(i).getDescription());
                ps.setString(7, list.get(i).getArticul());
                ps.setString(8, list.get(i).getFilename());
                ps.setFloat(9, list.get(i).getPrice());
                ps.setString(10, list.get(i).getExist() ? "T" : "F");

                ps.setLong(11, list.get(i).getId());
                ps.setLong(12, list.get(i).getOwner());
                ps.setString(13, list.get(i).getFolder() ? "T" : "F");
                ps.setString(14, list.get(i).getName());
                ps.setString(15, list.get(i).getDescription());
                ps.setString(16, list.get(i).getArticul());
                ps.setString(17, list.get(i).getFilename());
                ps.setFloat(18, list.get(i).getPrice());
                ps.setString(19, list.get(i).getExist() ? "T" : "F");

                ps.executeUpdate();
                
                LOG.info("[" + i + "] update good " + list.get(i).getName());
            }
            connection.commit();

			return "Uploaded " + list.size() + " goods succesfull";
		} catch (SQLException e) {
            LOG.error("something going wrong " + e);
			return e.getMessage();
		}
    }
}