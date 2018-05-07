package shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import shop.dao.CustomerDao;
import shop.dao.GoodDao;
import shop.dao.NewsDao;
import shop.dao.PartnerDao;
import shop.dao.PromoDao;
import shop.dao.RequestDao;
import shop.dao.TaleDao;
import shop.dao.impl.CustomerDaoImpl;
import shop.dao.impl.GoodDaoImpl;
import shop.dao.impl.PartnerDaoImpl;
import shop.dao.impl.PromoDaoImpl;
import shop.dao.impl.RequestDaoImpl;
import shop.dao.impl.NewsDaoImpl;
import shop.dao.impl.TaleDaoImpl;
import shop.entity.factory.impl.CustomerFactoryImpl;
import shop.entity.factory.impl.GoodFactoryImpl;

@Configuration
public class DaoConfig {
    
    @Bean("customerDao")
    @Scope("singleton")
    public CustomerDao customerDao() {
        return new CustomerDaoImpl(new CustomerFactoryImpl());
    }
    
    @Bean("goodDao")
    @Scope("singleton")
    public GoodDao goodDao() {
        return new GoodDaoImpl(new GoodFactoryImpl());
    }
    
    @Bean("requestDao")
    @Scope("singleton")
    public RequestDao requestDao() {
        return new RequestDaoImpl();
    }
    
    @Bean("partnerDao")
    @Scope("singleton")
    public PartnerDao partnerDao() {
        return new PartnerDaoImpl();
    }
    
    @Bean("promoDao")
    @Scope("singleton")
    public PromoDao promoDao() {
        return new PromoDaoImpl();
    }
    
    @Bean("newsDao")
    @Scope("singleton")
    public NewsDao newsDao() {
        return new NewsDaoImpl();
    }
    
    @Bean("taleDao")
    @Scope("singleton")
    public TaleDao taleDao() {
        return new TaleDaoImpl();
    }
    
}
