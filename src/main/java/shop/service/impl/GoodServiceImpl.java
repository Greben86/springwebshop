package shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.service.GoodService;
import shop.entity.Good;
import shop.dao.GoodDao; 

@Service("goodService")
public class GoodServiceImpl implements GoodService {
	@Autowired
	private GoodDao goodDao;

	@Override
	public Boolean delitionMarkForAll() {
		List<Good> list = goodDao.getList(""); 
		for (int i=0; i<list.size(); i++) {
			list.get(i).setDeletionmark(true);
			goodDao.update(list.get(i));
		}
		return true;
	}

	@Override
	public Boolean deleteMarked() {
		List<Good> list = goodDao.getList("`deletionmark`='T'");
		for (int i=0; i<list.size(); i++) {
			goodDao.delete(list.get(i));
		}
		return true;
	}

	@Override
	public Boolean updateOrInsert(Good customer) {
		goodDao.update(customer);
		return true;
	}

	@Override
	public Boolean deleteByRef(String ref) {
		Good entity = goodDao.findByRef(ref);
		if (entity!=null) {
			goodDao.delete(entity);
			return true;
		} else {
			return false;
		}		
	}
	
	@Override
	public List<Good> getCatalog() {
		return goodDao.getList("");
	}
}