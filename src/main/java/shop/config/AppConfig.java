package shop.config;

import java.io.IOException;
import java.nio.file.Paths;
import shop.model.*;
import shop.model.impl.*;
import shop.dao.*;
import shop.dao.impl.*;
import shop.service.*;
import shop.service.impl.*;
import shop.entity.factory.impl.*;

import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
@EnableCaching
@PropertySource(value = {"classpath:database.properties"})
@PropertySource(value = {"classpath:verification.properties"})
@PropertySource(value = {"classpath:files.properties"})
@PropertySource(value = {"classpath:search.properties"})
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment enviroment;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(5 * 1024 * 1024);
        return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        return multipartFilter;
    }

    @Bean
    @Scope("singleton")
    public DriverManagerDataSource dataSource() {
        Properties properties = new Properties();
        properties.setProperty("user", enviroment.getRequiredProperty("database.user"));
        properties.setProperty("password", enviroment.getRequiredProperty("database.password"));
        properties.setProperty("useUnicode", enviroment.getRequiredProperty("database.useUnicode"));
        properties.setProperty("characterEncoding", enviroment.getRequiredProperty("database.characterEncoding"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(enviroment.getRequiredProperty("database.driver"));
        dataSource.setUrl(enviroment.getRequiredProperty("database.url"));
        dataSource.setConnectionProperties(properties);

        return dataSource;
    }
    
    @Bean
    @Scope("singleton")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("folders");
    }

    @Bean
    @Scope("singleton")
    public VerificationRequest verificationRequest() {
        return new VerificationRequestImpl(enviroment.getRequiredProperty("verification.key"));
    }

    @Bean
    @Scope("singleton")
    public ImageControl imageControl() {
        return new ImageControlImpl(enviroment.getRequiredProperty("save.directory"));
    }

    @Bean
    @Scope("singleton")
    public CustomerService customerService() {
        return new CustomerServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public CustomerDao customerDao() {
        return new CustomerDaoImpl(new CustomerFactoryImpl());
    }

    @Bean
    @Scope("singleton")
    public GoodService goodService() {
        return new GoodServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public GoodDao goodDao() {
        return new GoodDaoImpl(new GoodFactoryImpl());
    }

    @Bean
    @Scope("request")
    public Search search() {
        return new SearchGoodImpl(enviroment.getRequiredProperty("search.index.directory"));
    }

//        @Bean
//        @Scope("request")
//        public Analyzer standardAnalyzer() {
//            Analyzer analyzer = new RussianAnalyzer();
//            return analyzer;
//        }
}
