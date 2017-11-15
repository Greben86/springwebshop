package shop.dao;

import shop.entity.Good;
import java.util.List;

public interface GoodDao extends BasicDao<Good> {
    /**
     * Update list good
     * @param list - list good
     */
    String updateList(List<Good> list);
}