package shop.dao;

import java.util.List;

public interface BasicDao<T> {

    List<T> getList(String filter);

    T findByRef(String ref);

    T updateOrInsert(T entity);

    T delete(T entity);
}