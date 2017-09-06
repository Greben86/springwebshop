package shop.model.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import shop.model.CustomerUpdate;
import shop.entity.Customer;
import shop.dao.CustomerDao;

public class CustomerUpdateImpl implements CustomerUpdate {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public String delitionmarkforall() {
		List<Customer> list = customerDao.getList("");
		for (int i=0; i<=list.size(); i++) {
			list.get(i).setDeletionmark(true);
			customerDao.update(list.get(i));
		}
		return "Deletion mark for all is Ok";
	}

	@Override
	public String deletemarked() {
		List<Customer> list = customerDao.getList("`deletionmark`='T'");
		for (int i=0; i<=list.size(); i++) {
			customerDao.delete(list.get(i));
		}
		return "Delete marked is Ok";
	}

	@Override
	public String update(String ref, String name, String number, String pass) {
		Customer entity = new Customer();
		entity.setRef(ref);
		entity.setNumber(number);
		entity.setName(name);
		entity.setPass(pass);
		customerDao.update(entity);
		return name + " is Ok";
	}

	@Override
	public String deleteByRef(String ref) {
		Customer entity = customerDao.findByRef(ref);
		if (entity!=null) {
			customerDao.delete(entity);
			return ref + " is Deleted";
		} else {
			return ref + " not found";
		}
		
	}
}