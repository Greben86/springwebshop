package shop.service.impl;

import java.util.List;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.service.GoodService;
import shop.entity.Good;
import shop.dao.GoodDao; 
import org.apache.log4j.Logger;

@Service("goodService")
public class GoodServiceImpl implements GoodService {
	private static final Logger LOG = Logger.getLogger(GoodServiceImpl.class);
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
		List<Good> list = goodDao.getList("(`FOLDER`='T')" + ((owner>=0) ? " AND (`OWNER`="+owner+")" : ""));
		checkFolders(list);
		return list;
	}

	@Override
	public List<Good> getList(Long owner) {
		List<Good> list = goodDao.getList((owner>=0) ? "(`OWNER`="+owner+")" : "");
		checkFolders(list);
		return list;
	}

	private void checkFolders(List<Good> list) {
		List<Good> source = goodDao.getList("");
		Iterator<Good> iterator = list.iterator();
		while (iterator.hasNext()) {
			Good good = iterator.next();
			if (good.getFolder()) {
				Integer count = calcChild(good, source);
				if (count==0) {
					iterator.remove();
				} else {
					good.setChildcount(count);
				}
			}
		}
	}

	private Integer calcChild(Good owner, List<Good> list) {
		Integer count = 0;
		for (Good good : list) {
			if (good.getOwner().equals(owner.getId())) {
				if (good.getFolder()) {
					count += calcChild(good, list);
				} else {
					count++;
				}				
			}
		}
        return count;
    }

	@Override
	public Good getById(Long id) {
		return goodDao.findById(id);
	}
}