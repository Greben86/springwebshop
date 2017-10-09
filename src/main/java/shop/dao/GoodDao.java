package shop.dao;

import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    Good getById(long id);

    Good findGoodByArticul(String articul);
}