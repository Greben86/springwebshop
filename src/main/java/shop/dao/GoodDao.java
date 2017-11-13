package shop.dao;

import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    void deletionMarkList(String filter);
}