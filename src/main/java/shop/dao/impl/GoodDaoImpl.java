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

			stmt.executeUpdate(
				"INSERT INTO `goods` (`id`, `owner`, `folder`, `ref`, `name`, `description`, `articul`, `dimension`, `filename`, `price`, `exist`, `deletionmark`) "+
                "VALUES ("+entity.getId()+
                    ", "+entity.getOwner()+
                    ", '"+(entity.getFolder()?"T":"F")+
                    "', '"+entity.getRef()+
                    "', '"+entity.getName()+
                    "', '"+entity.getDescription()+
                    "', '"+entity.getArticul()+
                    "', '"+entity.getDimension()+
                    "', '"+entity.getFilename()+
                    "', "+entity.getPrice()+
                    ", '"+(entity.getExist()?"T":"F")+
                    "', '"+(entity.getDeletionmark()?"T":"F")+
                "');");

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
}