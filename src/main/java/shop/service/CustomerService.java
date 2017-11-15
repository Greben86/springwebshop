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
    Boolean delete(Customer customer);
    
    /**
     * Checking pass for customer
     * @param number - number of customer
     * @param pass - checking password
     */
    Boolean checkPass(String number, String pass);

    /**
     * Method returning list of customers
     * @param owner - ID owner
     */
    List<Customer> getList();

    /**
     * Method returning customer
     * @param id - ID of customer
     */
    Customer getById(Long id);
}