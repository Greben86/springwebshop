package shop.dao;

import java.util.List;
import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    Good getById(long id);

    Good findGoodByArticul(String articul);

    String updateList(List<Good> list);
}