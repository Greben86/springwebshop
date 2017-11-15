package shop.service;

import java.util.List;

import shop.entity.Good;

public interface GoodService {
    /**
     * Update or insert good in to database
     * @param good - entity good
     */
    Boolean updateOrInsert(Good good);

    /**
     * Update good list in to database
     * @param list - list of goods
     */
    Boolean updateList(List<Good> list);
    
    /**
     * Delete good of database
     * @param good - entuty good
     */
    Boolean delete(Good good);

    /**
     * Method returning list of folders good
     * @param owner - ID owner
     */
    List<Good> getFolders(Long owner);

    /**
     * Method returning list of goods
     * @param owner - ID owner
     */
    List<Good> getList(Long owner);

    /**
     * Method returning good
     * @param id - ID of good
     */
    Good getById(Long id);
}