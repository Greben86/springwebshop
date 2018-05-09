package shop.service;

import java.util.List;

import shop.entity.Good;

public interface GoodService {
    /**
     * Update or insert good in to database
     * @param good - entity good
     * @return 
     */
    Boolean updateOrInsert(Good good);

    /**
     * Update good list in to database
     * @param list - list of goods
     * @return 
     */
    Boolean updateList(List<Good> list);
    
    /**
     * Delete good of database
     * @param good - entuty good
     * @return 
     */
    Boolean delete(Good good);

    /**
     * Method returning list of folders good
     * @param owner - ID owner
     * @return 
     */
    List<Good> getFolders(Long owner);

    /**
     * Method returning list of goods
     * @param owner - ID owner
     * @return 
     */
    List<Good> getList(Long owner);

    /**
     * Method returning good
     * @param id - ID of good
     * @return 
     */
    Good getById(Long id);
}