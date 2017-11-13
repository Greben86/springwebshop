package shop.dao;

import java.util.List;

public interface BasicDao<T> {

    List<T> getList(String filter);

    String updateList(List<T> list);

    T findById(Long id);

    T updateOrInsert(T entity);

    T delete(T entity);
}