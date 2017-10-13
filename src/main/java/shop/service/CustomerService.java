package shop.service;

import shop.entity.Customer;

public interface CustomerService {

    Boolean delitionMarkForAll();
    
    Boolean deleteMarked();
    
    Boolean updateOrInsert(Customer customer);
    
    Boolean deleteByRef(String ref);
    
    Boolean checkPass(String number, String pass);

    Customer getCustomerByRef(String ref);
}