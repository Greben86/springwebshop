package shop.service;

import java.util.List;
import shop.entity.Customer;

public interface CustomerService {

    List<Customer> getList();

    Boolean delitionMarkForAll();
    
    Boolean deleteMarked();
    
    Boolean updateOrInsert(Customer customer);

    Boolean updateList(List<Customer> list);
    
    Boolean delete(Customer customer);
    
    Boolean checkPass(String number, String pass);

    Customer getById(Long id);
}