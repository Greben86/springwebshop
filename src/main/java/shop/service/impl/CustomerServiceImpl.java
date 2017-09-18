package shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.service.CustomerService;
import shop.entity.Customer;
import shop.dao.CustomerDao;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Boolean delitionMarkForAll() {
		List<Customer> list = customerDao.getList("");
		for (int i=0; i<list.size(); i++) {
			list.get(i).setDeletionmark(true);
			customerDao.update(list.get(i));
		}
		return true;
	}

	@Override
	public Boolean deleteMarked() {
		List<Customer> list = customerDao.getList("`deletionmark`='T'");
		for (int i=0; i<list.size(); i++) {
			customerDao.delete(list.get(i));
		}
		return true;
	}

	@Override
	public Boolean updateOrInsert(Customer customer) {
		customerDao.update(customer);
		return true;
	}

	@Override
	public Boolean deleteByRef(String ref) throws Exception {
		Customer entity = customerDao.findByRef(ref);
		if (entity!=null) {
			customerDao.delete(entity);
			return true;
		} else {
			return false;
		}		
    }
    
    @Override
    public Boolean checkPass(String number, String pass) {
        Customer entity = customerDao.findCustomerByNumber(number);
		if (entity!=null) {
			return pass.equalsIgnoreCase(entity.getPass());
		} else {
			return false;
		}
    }
}