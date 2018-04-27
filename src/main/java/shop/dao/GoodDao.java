package shop.dao;

import shop.entity.Good;
import java.util.List;

public interface GoodDao extends BasicDao<Good> {
    
    /**
     * Get list of entities
     * @param filter - sql-filter of list
     */
    List<Good> getList(String filter);
    
    /**
     * Update or insert entity
     * @param enetity - entity
     */
    Good updateOrInsert(Good good);
    
    /**
     * Update list good
     * @param list - list good
     */
    String updateList(List<Good> list);
}