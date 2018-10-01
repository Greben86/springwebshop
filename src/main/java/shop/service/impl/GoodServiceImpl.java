package shop.service.impl;

import java.util.List;
import java.util.Iterator;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.service.GoodService;
import shop.entity.Good;
import shop.dao.GoodDao;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("goodService")
@Transactional(readOnly = true)
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "folders", key = "#good.id")
    @Override
    public Boolean updateOrInsert(Good good) {
        goodDao.updateOrInsert(good);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "folders", allEntries = true)
    @Override
    public Boolean updateList(List<Good> list) {
        list.forEach(good -> updateOrInsert(good));
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "folders", key = "#good.id")
    @Override
    public void delete(Good good) {
        goodDao.delete(good);
    }

    @Cacheable("folders")
    @Override
    public List<Good> getFolders(Long owner) {
        List<Good> list = goodDao.getList("(`FOLDER`='T')"
                + ((owner >= 0) ? " AND (`OWNER`=" + owner + ")" : ""));
        if (Objects.nonNull(list)) {
            checkFolders(list);
        }
        return list;
    }

    @Override
    public List<Good> getList(Long owner) {
        List<Good> list = goodDao.getList(
                (owner >= 0) ? "(`OWNER`=" + owner + ")" : "");
        if (Objects.nonNull(list)) {
            checkFolders(list);
        }
        return list;
    }

    private void checkFolders(List<Good> list) {
        List<Good> source = goodDao.getList();
        Iterator<Good> iterator = list.iterator();
        while (iterator.hasNext()) {
            Good good = iterator.next();
            if (good.getFolder()) {
                if (calcChild(good, source) == 0) {
                    iterator.remove();
                }
            }
        }
    }

    private Integer calcChild(Good owner, List<Good> list) {
        list.stream()
                .filter((Good good) -> good.getOwner().equals(owner.getId()))
                .forEach((Good good) -> {
                    if (good.getFolder()) {
                        owner.setHaschild(Boolean.TRUE);
                        owner.setChildcount(owner.getChildcount() + calcChild(good, list));
                    } else {
                        owner.setChildcount(owner.getChildcount() + 1);
                    }
                });
        return owner.getChildcount();
    }

    @Override
    public Good getById(Long id) {
        return goodDao.findById(id);
    }
}
