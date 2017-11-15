package shop.dao;

import shop.entity.Customer;
import java.util.List;

public interface CustomerDao extends BasicDao<Customer> {

    public Customer findCustomerByNumber(String number);
    
    /**
     * Update list customers
     * @param list - list customers
     */
    String updateList(List<Customer> list);
}