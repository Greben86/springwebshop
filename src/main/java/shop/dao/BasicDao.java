package shop.dao;

import java.util.List;

public interface BasicDao<T> {
    /**
     * Get list of entities
     * @param filter - sql-filter of list
     */
    List<T> getList(String filter);

    /**
     * Find entity by id
     * @param id - id of entity
     */
    T findById(Long id);

    /**
     * Update or insert entity
     * @param enetity - entity
     */
    T updateOrInsert(T entity);

    /**
     * Delete entity
     * @param entity - entity
     */
    T delete(T entity);
}