package shop.dao;

import java.util.List;
import java.util.function.Predicate;
import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    List<Good> getList(String filter, Predicate<Long> c);
    
    Good getById(long id);

    Good findGoodByArticul(String articul);

    String updateList(List<Good> list);

    Boolean hasChild(long id);
}