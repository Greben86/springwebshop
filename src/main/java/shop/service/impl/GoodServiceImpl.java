package shop.service.impl;

import java.util.List;
import java.util.Iterator;
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
		List<Good> source = goodDao.getList("");
		List<Good> result = goodDao.getList("(`FOLDER`='T')" + ((owner>=0) ? " AND (`OWNER`="+owner+")" : ""));
		Iterator<Good> iterator = result.iterator();
		Good good;
		Integer count;
		while (iterator.hasNext()) {
			good = iterator.next();
			count = hasChild(good, source);
			if (count==0) {
				iterator.remove();
			}
		}
		return result;
	}

	@Override
	public List<Good> getList(Long owner) {
		List<Good> source = goodDao.getList("");
		List<Good> result = goodDao.getList((owner>=0) ? "(`OWNER`="+owner+")" : "");
		Iterator<Good> iterator = result.iterator();
		Good good;
		Integer count;
		while (iterator.hasNext()) {
			good = iterator.next();
			if (good.getFolder()) {
				count = hasChild(good, source);
				if (count==0) {
					iterator.remove();
				}
			}
		}
		return result;
	}

	private Integer hasChild(Good owner, List<Good> list) {
		Integer count = 0;
		for (Good good : list) {
			if (good.getFolder()) {
				count += hasChild(good, list);
			} else
			if (good.getOwner().equals(owner.getId())) {
				count++;
			}
		}
        return count;
    }

	@Override
	public Good getById(Long id) {
		return goodDao.findById(id);
	}
}