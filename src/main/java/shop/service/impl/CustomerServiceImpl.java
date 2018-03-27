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

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateOrInsert(Customer customer) {
        if ("".equals(customer.getRef())) {
            return false;
        }

        Customer buff = customerDao.findByRef(customer.getRef());
        if (buff == null && !"".equals(customer.getEmail())) {
            buff = customerDao.findByEmail(customer.getEmail());
        }

        if (buff != null) {
            customer.setId(buff.getId());
            customerDao.update(customer);
        } else {
            customerDao.create(customer);
        }
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean updateList(List<Customer> list) {
        list.stream().forEach(customer -> updateOrInsert(customer));
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
    public Boolean checkPass(String login, String pass) {
        Customer entity = null;
        if ((entity = customerDao.findByNumber(login)) == null) {
            entity = customerDao.findByEmail(login);
        }
        
        if (entity != null) {
            return pass.equalsIgnoreCase(entity.getPass());
        } else {
            return false;
        }
    }

    @Override
    public Customer getByRef(String ref) {
        return customerDao.findByRef(ref);
    }
}
