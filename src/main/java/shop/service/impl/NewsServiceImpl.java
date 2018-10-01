package shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.dao.NewsDao;
import shop.entity.News;
import shop.service.NewsService;

@Service("newsService")
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {
    
    @Autowired
    private NewsDao newsDao;

    @Override
    public List<News> getList() {
        return newsDao.getList();
    }
    
    @Override
    public News findById(Long id) {
        return newsDao.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void create(News news) {
        newsDao.create(news);
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(News news) {
        newsDao.update(news);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(News news) {
        newsDao.delete(news);
    }
    
}
