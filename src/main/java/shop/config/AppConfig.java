package shop.config;

import shop.model.*;
import shop.model.impl.*;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
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
    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) 
                    throws UsernameNotFoundException {
                return new User("admin", "qwerty123",
                        true, true, true, true,
                        AuthorityUtils.createAuthorityList("ADMIN"));
            }
        };
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DriverManagerDataSource dataSource() {
        Properties properties = new Properties();
        properties.setProperty("user", 
                enviroment.getRequiredProperty("database.user"));
        properties.setProperty("password", 
                enviroment.getRequiredProperty("database.password"));
        properties.setProperty("useUnicode", 
                enviroment.getRequiredProperty("database.useUnicode"));
        properties.setProperty("characterEncoding", 
                enviroment.getRequiredProperty("database.characterEncoding"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                enviroment.getRequiredProperty("database.driver"));
        dataSource.setUrl(enviroment.getRequiredProperty("database.url"));
        dataSource.setConnectionProperties(properties);

        return dataSource;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = 
                new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("folders");
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ImageControl imageControl() {
        return new ImageControlImpl(
                enviroment.getRequiredProperty("save.directory"));
    }

    @Bean("search")
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, 
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Search search() {
        return new SearchGoodImpl(
                enviroment.getRequiredProperty("search.index.directory"));
    }
}
