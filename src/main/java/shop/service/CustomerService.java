package shop.service;

import java.util.List;
import shop.entity.Customer;

public interface CustomerService {  
    /**
     * Update or insert customer in to database
     * @param customer - entity customer
     */
    Boolean updateOrInsert(Customer customer);

    /**
     * Update customer list in to database
     * @param list - list of customers
     */
    Boolean updateList(List<Customer> list);
    
    /**
     * Delete customer of database
     * @param customer - entuty customer
     */
    void delete(Customer customer);
    
    /**
     * Searching customer by login
     * @param login - number or email of customer
     */
    Customer search(String login);
    
    /**
     * Checking pass for customer
     * @param customer - checked customer
     * @param pass - checked password
     */    
    Boolean checkPass(Customer customer, String pass);

    /**
     * Method returning list of customers
     * @param owner - ID owner
     */
    List<Customer> getList();

    /**
     * Method returning customer
     * @param ref - Ref of customer
     */
    Customer getByRef(String ref);
}