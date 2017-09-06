package shop.model.impl;

import org.springframework.beans.factory.annotation.Autowired;

import shop.model.CustomerAuth;
import shop.entity.Customer;
import shop.dao.CustomerDao;

public class CustomerAuthImpl implements CustomerAuth {
    @Autowired
	private CustomerDao customerDao;

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