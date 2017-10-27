package shop.dao;

import java.util.List;
import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {
    
    Good getById(long id);

    String updateList(List<Good> list);

    void deletionMarkList(String filter);
}