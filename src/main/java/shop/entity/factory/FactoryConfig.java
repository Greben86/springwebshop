package shop.entity.factory;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import shop.entity.News;
import shop.entity.Partner;
import shop.entity.Promo;
import shop.entity.RequestToAppend;
import shop.entity.Tale;
import shop.entity.factory.impl.NewsFactoryImpl;
import shop.entity.factory.impl.PartnerFactoryImpl;
import shop.entity.factory.impl.PromoFactoryImpl;
import shop.entity.factory.impl.RequestFactoryImpl;
import shop.entity.factory.impl.TaleFactoryImpl;

@Configuration
public class FactoryConfig {
    
    @Bean("requestFactory")
    @Scope("singleton")
    public BasicFactory<RequestToAppend> requestFactory() {
        return new RequestFactoryImpl();
    }
    
    @Bean("partnerFactory")
    @Scope("singleton")
    public BasicFactory<Partner> partnerFactory() {
        return new PartnerFactoryImpl();
    }
    
    @Bean("promoFactory")
    @Scope("singleton")
    public BasicFactory<Promo> promoFactory() {
        return new PromoFactoryImpl();
    }
    
    @Bean("newsFactory")
    @Scope("singleton")
    public BasicFactory<News> newsFactory() {
        return new NewsFactoryImpl();
    }
    
    @Bean("taleFactory")
    @Scope("singleton")
    public BasicFactory<Tale> taleFactory() {
        return new TaleFactoryImpl();
    }
}
