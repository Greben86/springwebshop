package shop.service.impl;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.service.CustomerService;
import shop.entity.Customer;
import shop.dao.CustomerDao;
import static java.util.Optional.ofNullable;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> getList() {
        return customerDao.getList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateOrInsert(Customer customer) {
        if (customer.getRef().isEmpty()) {
            return false;
        }

        Customer buff = ofNullable(customerDao.findByRef(customer.getRef()))
                .filter(c -> !c.getEmail().isEmpty())
                .orElse(customerDao.findByEmail(customer.getEmail()));

        if (Objects.nonNull(buff)) {
            customer.setId(buff.getId());
            customerDao.update(customer);
            if (buff.getPass().isEmpty()) {
                customerDao.updatePass(customer, customer.getPass());
            }
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
        if (Objects.isNull(customer)) {
            return false;
        }
        
        customerDao.delete(customer);
        return true;
    }

    @Override
    public Customer search(String login) {
        return ofNullable(customerDao.findByNumber(login))
                .orElse(customerDao.findByEmail(login));
    }
    
    @Override
    public Boolean checkPass(Customer customer, String pass) {
        return ofNullable(customer)
                .map(entity -> pass.equalsIgnoreCase(entity.getPass()))
                .orElse(Boolean.FALSE);
    }

    @Override
    public Customer getByRef(String ref) {
        return customerDao.findByRef(ref);
    }
}
