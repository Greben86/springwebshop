package shop.service;

import java.util.List;

import shop.entity.Good;

public interface GoodService {
    
    Boolean updateOrInsert(Good good);

    Boolean updateList(List<Good> list);
    
    Boolean delete(Good good);

    List<Good> getFolders(Long owner);

    List<Good> getList(Long owner);

    Good getById(Long id);
}