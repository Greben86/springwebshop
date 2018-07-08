package shop.entity.factory.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.entity.Tale;
import shop.entity.factory.BasicFactory;

public class TaleFactoryImpl implements BasicFactory<Tale> {

    @Override
    public Tale factory(ResultSet set) throws SQLException {
        Tale tale = new Tale();
        tale.setId(set.getLong("id"));
        tale.setTitle(set.getString("title"));
        tale.setBody(set.getString("body"));
        tale.setFilename(set.getString("filename"));
        tale.setDate(set.getDate("date"));
        tale.setEnabled(set.getBoolean("enabled"));
        return tale;
    }
    
}
