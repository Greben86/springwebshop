package shop.entity.factory.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.entity.Partner;
import shop.entity.factory.BasicFactory;

public class PartnerFactoryImpl implements BasicFactory<Partner> {

    @Override
    public Partner factory(ResultSet set) throws SQLException {
        Partner partner = new Partner();
        partner.setId(set.getLong("id"));
        partner.setNote(set.getString("note"));
        partner.setLink(set.getString("link"));
        partner.setDiscount(set.getString("discount"));
        partner.setOrdr(set.getInt("ordr"));
        return partner;
    }
    
}
