package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ResourceImages;
import shop.dao.PartnerDao;
import shop.entity.Partner;
import shop.model.ImageControl;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/partners")
public class PartnersController {
    
    @Autowired
    private PartnerDao partnersDao;
    @Autowired
    private ImageControl imageControl;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Partner> getList() {
        return partnersDao.getList();
    }
    
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, 
        MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        return ofNullable(partnersDao.findById(Long.parseLong(id)))
                .map(partner -> imageControl.readFile(
                        "partners/"+partner.getFilename(), 
                        ResourceImages.DEFAULT_IMAGE))
                .orElse(new byte[0]);
    }
    
}
