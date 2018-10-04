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
    void updateOrInsert(Good good);
}