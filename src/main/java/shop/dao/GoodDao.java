package shop.dao;

import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    public Good findGoodByArticul(String articul);
}