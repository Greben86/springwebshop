package shop.entity.factory.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.entity.RequestToAppend;
import shop.entity.factory.BasicFactory;

public class RequestFactoryImpl implements BasicFactory<RequestToAppend> {

    @Override
    public RequestToAppend factory(ResultSet set) throws SQLException {
        RequestToAppend request = new RequestToAppend();
        request.setDate(set.getDate("date_doc"));
        request.setFullname(set.getString("fullname"));
        request.setFamily(set.getString("family"));
        request.setName(set.getString("name"));
        request.setName2(set.getString("name2"));
        request.setEmail(set.getString("email"));
        request.setPhone(set.getString("phone"));
        request.setNote(set.getString("note"));
        return request;
    }

}
