package shop.entity.factory.impl;

import shop.entity.factory.BasicFactory;
import shop.entity.Good;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodFactoryImpl implements BasicFactory<Good> {

    @Override
    public Good factory(ResultSet set) throws SQLException {
        Good good = new Good(set.getLong("id"));
        good.setOwner(set.getLong("owner"));
        good.setFolder(set.getString("folder").equals("T"));
        good.setName(set.getString("name"));
        good.setDescription(set.getString("description"));
        good.setArticle(set.getString("article"));
        good.setPrice(set.getFloat("price"));
        good.setInstock(set.getFloat("instock"));
        return good;
    }
}
