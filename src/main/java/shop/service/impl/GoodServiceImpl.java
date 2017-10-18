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
	public Boolean updateList(List<Good> list) {
		LOG.info("Start update goods (count: " + list.size() + ")");
		for (int i=0; i<list.size(); i++) {
			LOG.info("Update good " + list.get(i).getName());
			goodDao.update(list.get(i));
		}
		LOG.info("Finish update goods (count: " + list.size() + ")");
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