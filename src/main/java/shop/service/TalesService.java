package shop.service;

import java.util.List;
import shop.entity.Tale;

public interface TalesService {

    List<Tale> getList();

    Tale findById(Long id);

    void save(Tale tale);

    void delete(Tale tale);
    
}
