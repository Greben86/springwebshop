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
		// List<Good> list = goodDao.getList("`deletionmark`='T'");
		// for (Good entity : list) {
		// 	goodDao.delete(entity);
		// }
		return true;
	}

	@Override
	public Boolean updateOrInsert(Good customer) {
		goodDao.updateOrInsert(customer);
		return true;
	}

	@Override
	public Boolean updateList(List<Good> list) {
		goodDao.updateList(list);
		return true;
	}

	@Override
	public Boolean deleteById(Long id) {
		Good entity = goodDao.findById(id);
		if (entity!=null) {
			goodDao.delete(entity);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Good> getFolders(Long owner) {
		if (owner>=0) {
			return goodDao.getList(String.format("(`FOLDER`='T') AND (`OWNER`=%d)", owner));
		} else {
			return goodDao.getList("(`FOLDER`='T')");
		}
	}

	@Override
	public List<Good> getList(Long owner) {
		if (owner>=0) {
			return goodDao.getList(String.format("`OWNER`=%d", owner));
		} else {
			return goodDao.getList("");
		}
	}

	@Override
	public Good getById(Long id) {
		return goodDao.findById(id);
	}
}