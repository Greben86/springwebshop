package shop.dao.impl;

import shop.dao.GoodDao;
import shop.entity.Good;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class GoodDaoImpl implements GoodDao {
    @Autowired
	private DriverManagerDataSource dataSource;

    @Override
    public List<Good> getList(String filter) {
        List<Good> result = new ArrayList<Good>();
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` " + (filter.equals("") ? "" : "WHERE "+filter));
            
            while (rs.next()) {
                Good e = new Good();
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setPrice(rs.getFloat("price"));
                e.setExist(rs.getBoolean("exist"));
                result.add(e);
            }

			return result;
		} catch (SQLException e) {
			return result;
		}
    }
    
    @Override
    public Good findByRef(String ref) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` WHERE `ref`='"+ref+"'");
            
            if (rs.next()) {
                Good e = new Good();
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setPrice(rs.getFloat("price"));
                e.setExist(rs.getBoolean("exist"));
    
                return e;
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

			stmt.executeUpdate(
				"INSERT INTO `goods` (`ref`, `name`, `description`, `articul`, `dimension`, `price`, `exist`, `deletionmark`) "+
				"VALUES ('"+entity.getRef()+"', '"+entity.getName()+"', '"+entity.getDescription()+"', '"+entity.getArticul()+"', '"+entity.getDimension()+"', "+entity.getPrice()+", '"+(entity.getExist()?"T":"F")+"', '"+(entity.getDeletionmark()?"T":"F")+"');");

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

			stmt.execute("DELETE FROM `goods` WHERE `ref`='"+entity.getRef()+"';");

			return entity;
		} catch (SQLException e) {
			return entity;
		}
    }

    @Override
    public Good findGoodByArticul(String articul) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` WHERE `articul`='"+articul+"'");
            
            if (rs.next()) {
                Good e = new Good();
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setPrice(rs.getFloat("price"));
                e.setExist(rs.getBoolean("exist"));
    
                return e;
            } else {
                return null;
            }            
		} catch (SQLException e) {
			return null;
		}
    }
}