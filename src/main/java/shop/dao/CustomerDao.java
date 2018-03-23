package shop.dao;

import shop.entity.Customer;
import java.util.List;

public interface CustomerDao extends BasicDao<Customer> {

    public Customer findByRef(String ref);
    
    public Customer findByNumber(String number);
    
    public Customer findByEmail(String email);
    
    public void create(Customer customer);
    
    public void update(Customer customer);
    
    /**
     * Update list customers
     * @param list - list customers
     * @return - message
     */
    String updateList(List<Customer> list);
}