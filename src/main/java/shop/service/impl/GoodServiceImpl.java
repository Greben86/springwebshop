package shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import shop.service.GoodService;
import shop.entity.Good;
import shop.dao.GoodDao; 

@Service("goodService")
public class GoodServiceImpl implements GoodService {
	private static final Logger LOG = Logger.getLogger(GoodServiceImpl.class);
	@Autowired
	private GoodDao goodDao;

	@Override
	public Boolean delitionMarkForAll() {
		goodDao.deletionMarkList("");
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
		goodDao.updateOrInsert(customer);
		return true;
	}

	@Override
	public Boolean updateList(List<Good> list) {
		goodDao.deletionMarkList("");
		goodDao.updateList(list);
		deleteMarked();
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
	public List<Good> getFolders(Long owner) {
		return goodDao.getList(String.format("(`FOLDER`='T') AND (`OWNER`=%d)", owner));
	}

	@Override
	public List<Good> getList(Long owner) {
		return goodDao.getList(String.format("`OWNER`=%d", owner));
	}

	@Override
	public Good getById(Long id) {
		return goodDao.getById(id);
	}
}