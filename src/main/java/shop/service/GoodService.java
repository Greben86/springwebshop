package shop.service;

import java.util.List;

import shop.entity.Good;

public interface GoodService {

    Boolean delitionMarkForAll();
    
    Boolean deleteMarked();
    
    Boolean updateOrInsert(Good customer);
    
    Boolean deleteByRef(String ref);

    List<Good> getFolders(Long owner);

    List<Good> getList(Long owner);

    Good getById(long id);
}