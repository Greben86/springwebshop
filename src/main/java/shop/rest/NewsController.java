package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.entity.News;
import shop.dao.NewsDao;
import shop.model.ImageControl;

@RestController
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private ImageControl imageControl;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<News> getList() {
        return newsDao.getList();
    }
    
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable("id") String id) {
        News news = newsDao.findById(Long.parseLong(id));
        if (news != null) {
            return imageControl.readFile(news.getFilename(), "noimagegood.png");
        } else {
            return null;
        }
    }
    
}
