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

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` " + (filter.equals("") ? "" : "WHERE "+filter));
            
            List<Good> result = new ArrayList<Good>();
            while (rs.next()) {
                Good e = new Good();
                e.setId(rs.getLong("id"));
                e.setOwner(rs.getLong("owner"));
                e.setFolder(rs.getBoolean("folder"));
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setFilename(rs.getString("filename"));
                e.setPrice(rs.getFloat("price"));
                e.setExist(rs.getBoolean("exist"));
                result.add(e);
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

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` WHERE `ref`='"+ref+"'");
            
            if (rs.next()) {
                Good e = new Good();
                e.setId(rs.getLong("id"));
                e.setOwner(rs.getLong("owner"));
                e.setFolder(rs.getBoolean("folder"));
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setFilename(rs.getString("filename"));
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

			stmt.execute("DELETE FROM `goods` WHERE `ref`='"+entity.getRef()+"';");

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

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` WHERE `id`="+id+"");
            
            if (rs.next()) {
                Good e = new Good();
                e.setId(rs.getLong("id"));
                e.setOwner(rs.getLong("owner"));
                e.setFolder(rs.getBoolean("folder"));
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setFilename(rs.getString("filename"));
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
    public Good findGoodByArticul(String articul) {
        try (
			Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM `goods` WHERE `articul`='"+articul+"'");
            
            if (rs.next()) {
                Good e = new Good();
                e.setId(rs.getLong("id"));
                e.setOwner(rs.getLong("owner"));
                e.setFolder(rs.getBoolean("folder"));
                e.setRef(rs.getString("ref"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setArticul(rs.getString("articul"));
                e.setDimension(rs.getString("dimension"));
                e.setFilename(rs.getString("filename"));
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