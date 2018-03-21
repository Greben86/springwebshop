package shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.service.CustomerService;
import shop.entity.Customer;
import shop.dao.CustomerDao;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> getList() {
        return customerDao.getList("");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean updateOrInsert(Customer customer) {
        customerDao.updateOrInsert(customer);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean updateList(List<Customer> list) {
        customerDao.updateList(list);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean delete(Customer customer) {
        if (customer != null) {
            customerDao.delete(customer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkPass(String number, String pass) {
        Customer entity = customerDao.findCustomerByNumber(number);
        if (entity != null) {
            return pass.equalsIgnoreCase(entity.getPass());
        } else {
            return false;
        }
    }

    @Override
    public Customer getById(Long id) {
        return customerDao.findById(id);
    }
}
