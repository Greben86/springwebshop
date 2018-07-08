package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.entity.Tale;
import shop.dao.TaleDao;
import shop.model.ImageControl;

@RestController
@RequestMapping("/tales")
public class TalesController {
    
    @Autowired
    private TaleDao taleDao;
    @Autowired
    private ImageControl imageControl;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Tale> getList() {
        return taleDao.getList();
    }
    
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        Tale tale = taleDao.findById(Long.parseLong(id));
        if (tale != null) {
            return imageControl.readFile(tale.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
    
}
