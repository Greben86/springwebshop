package shop.service;

import java.util.List;
import shop.entity.Partner;

public interface PartnersService {

    List<Partner> getList();

    Partner findById(Long id);

    void save(Partner partner);

    void delete(Partner partner);
}
