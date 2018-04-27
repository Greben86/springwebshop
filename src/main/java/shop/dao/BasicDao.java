package shop.dao;

import java.util.List;

public interface BasicDao<T> {

    /**
     * Get list of entities
     *
     * @param filter - sql-filter of list
     */
    List<T> getList();

    /**
     * Find entity by id
     *
     * @param id - id of entity
     */
    T findById(Long id);

    /**
     * Insert entity
     *
     * @param enetity - entity
     */
    void create(T entity);

    /**
     * Update entity
     *
     * @param enetity - entity
     */
    void update(T entity);

    /**
     * Delete entity
     *
     * @param id - primary key entity
     */
    void delete(Long id);
}
