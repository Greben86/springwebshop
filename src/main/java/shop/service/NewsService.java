package shop.service;

import java.util.List;
import shop.entity.News;

public interface NewsService {
    
    List<News> getList();
    
    News findById(Long id);
    
    void create(News news);
    
    void update(News news);
    
    void delete(News news);    
}
