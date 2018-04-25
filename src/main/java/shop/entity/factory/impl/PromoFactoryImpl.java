package shop.entity.factory.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.entity.Promo;
import shop.entity.factory.BasicFactory;

public class PromoFactoryImpl implements BasicFactory<Promo> {

    @Override
    public Promo factory(ResultSet set) throws SQLException {
        Promo promo = new Promo();
        promo.setId(set.getLong("id"));
        promo.setName(set.getString("name"));
        return promo;
    }
    
}
