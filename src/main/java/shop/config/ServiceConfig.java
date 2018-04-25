package shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import shop.service.CustomerService;
import shop.service.GoodService;
import shop.service.impl.CustomerServiceImpl;
import shop.service.impl.GoodServiceImpl;

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
    
}
