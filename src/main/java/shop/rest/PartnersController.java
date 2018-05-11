package shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dao.PartnerDao;
import shop.entity.Partner;
import shop.model.ImageControl;

@RestController
@RequestMapping("/partners")
public class PartnersController {
    
    @Autowired
    private PartnerDao partnersDao;
    @Autowired
    private ImageControl imageControl;
    
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        Partner partner = partnersDao.findById(Long.parseLong(id));
        if (partner != null) {
            return imageControl.readFile("partners/"+partner.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
    
}
