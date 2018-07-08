package shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import shop.service.CustomerService;
import shop.service.GoodService;
import shop.service.NewsService;
import shop.service.impl.CustomerServiceImpl;
import shop.service.impl.GoodServiceImpl;
import shop.service.impl.NewsServiceImpl;

@Configuration
public class ServiceConfig {
    
    @Bean("customerService")
    @Scope("singleton")
    public CustomerService customerService() {
        return new CustomerServiceImpl();
    }

    @Bean("goodService")
    @Scope("singleton")
    public GoodService goodService() {
        return new GoodServiceImpl();
    }
    
    @Bean("newsService")
    @Scope("singleton") 
    public NewsService newsService() {
        return new NewsServiceImpl();
    }
    
}
