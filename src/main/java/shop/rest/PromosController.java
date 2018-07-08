package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dao.PromoDao;
import shop.entity.Promo;
import shop.model.ImageControl;

@RestController
@RequestMapping("/promos")
public class PromosController {
    
    @Autowired
    private PromoDao promoDao;
    @Autowired
    private ImageControl imageControl;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE) 
    public List<Promo> getList() {
        return promoDao.getList();
    }
    
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        Promo promo = promoDao.findById(Long.parseLong(id));
        if (promo != null) {
            return imageControl.readFile("promos/"+promo.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
    
}
