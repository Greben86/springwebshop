package shop.entity.factory.impl;

import shop.entity.factory.BasicFactory;
import shop.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFactoryImpl implements BasicFactory<Customer> {
    public Customer factory(ResultSet set) throws SQLException {
        Customer customer = new Customer();
        customer.setId(set.getLong("id"));
        customer.setRef(set.getString("ref"));
        customer.setNumber(set.getString("number"));
        customer.setName(set.getString("name"));
        customer.setFullname(set.getString("fullname"));
        customer.setEmail(set.getString("email"));
        customer.setPass(set.getString("pass"));
        return customer;
    }
}