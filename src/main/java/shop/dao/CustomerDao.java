package shop.dao;

import shop.entity.Customer;
import java.util.List;

public interface CustomerDao extends BasicDao<Customer> {

    public Customer findByRef(String ref);
    
    public Customer findByNumber(String number);
    
    public Customer findByEmail(String email);
    
    public void updatePass(Customer customer, String pass);
    
    /**
     * Update list customers
     * @param list - list customers
     * @return - message
     */
    String updateList(List<Customer> list);
    
    void readDetail(Customer customer);
    
    void saveDetail(Customer customer);
    
    void clearDetail(Customer customer);
}