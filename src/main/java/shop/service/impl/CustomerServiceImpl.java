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
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> getList() {
        List<Customer> result = customerDao.getList();
        result.stream().forEach(item -> customerDao.readDetail(item));
        return result;
    }

    private Customer findFromRepository(Customer customer) {
        return ofNullable(customerDao.findByRef(customer.getRef()))
                .filter(c -> !c.getEmail().isEmpty())
                .orElse(customerDao.findByEmail(customer.getEmail()));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, 
            propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateOrInsert(Customer customer) {
        if (customer.getRef().isEmpty()) {
            return false;
        }

        Customer buff = findFromRepository(customer);

        if (Objects.nonNull(buff)) {
            customer.setId(buff.getId());
            customerDao.update(customer);
            if (buff.getPass().isEmpty()) {
                customerDao.updatePass(customer, customer.getPass());
            }
        } else {
            customerDao.create(customer);
            ofNullable(customerDao.findByEmail(customer.getEmail()))
                    .ifPresent(c -> customer.setId(c.getId()));
        }
        customerDao.saveDetail(customer);

        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, 
            propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean updateList(List<Customer> list) {
        list.stream()
                .forEach(customer -> updateOrInsert(customer));
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, 
            propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(Customer customer) {
        ofNullable(customer)
                .ifPresent(customerDao::delete);
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
        Customer customer = customerDao.findByRef(ref);
        customerDao.readDetail(customer);
        return customer;
    }
}
