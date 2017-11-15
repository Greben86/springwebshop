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
	public Boolean updateOrInsert(Good good) {
		goodDao.updateOrInsert(good);
		return true;
	}

	@Override
	public Boolean updateList(List<Good> list) {
		goodDao.updateList(list);		
		return true;
	}

	@Override
	public Boolean delete(Good good) {
		if (good!=null) {
			goodDao.delete(good);
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