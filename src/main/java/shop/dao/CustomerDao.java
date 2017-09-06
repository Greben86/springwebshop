package shop.dao;

import shop.entity.Customer;

public interface CustomerDao extends BasicDao<Customer> {

    public Customer findCustomerByNumber(String number);
}