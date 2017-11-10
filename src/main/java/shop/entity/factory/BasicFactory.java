package shop.entity.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BasicFactory<T> {
    /**
     * Factory create Good
     * @param set - set fields of good
     */
    T factory(ResultSet set) throws SQLException;
}