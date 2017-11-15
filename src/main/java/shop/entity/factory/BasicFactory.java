package shop.entity.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BasicFactory<T> {
    /**
     * Factory create entity
     * @param set - set fields of entity
     */
    T factory(ResultSet set) throws SQLException;
}