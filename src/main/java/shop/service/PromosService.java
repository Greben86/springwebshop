package shop.service;

import java.util.List;
import shop.entity.Promo;

public interface PromosService {

    List<Promo> getList();

    Promo findById(Long id);

    void save(Promo promo);

    void delete(Promo promo);
}
