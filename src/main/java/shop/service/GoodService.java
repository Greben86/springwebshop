package shop.service;

import shop.entity.Good;

public interface GoodService {

    Boolean delitionMarkForAll();
    
    Boolean deleteMarked();
    
    Boolean updateOrInsert(Good customer);
    
    Boolean deleteByRef(String ref);
}