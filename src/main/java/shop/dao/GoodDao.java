package shop.dao;

import java.util.List;
import shop.entity.Good;

public interface GoodDao extends BasicDao<Good> {

    String updateList(List<Good> list);

    void deletionMarkList(String filter);
}