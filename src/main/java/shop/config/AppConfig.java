package shop.config;

import shop.model.*;
import shop.model.impl.*;
import shop.dao.*;
import shop.dao.impl.*;
import shop.service.*;
import shop.service.impl.*;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
@PropertySource(value = {"classpath:database.properties"})
@PropertySource(value = {"classpath:verification.properties"})
@PropertySource(value = {"classpath:files.properties"})
public class AppConfig {
	@Autowired
	private Environment enviroment;

	@Bean 
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipart = new CommonsMultipartResolver();
		multipart.setMaxUploadSize(3 * 1024 * 1024);
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
		return new CustomerDaoImpl();
	}

	@Bean
	@Scope("singleton")
	public GoodService goodService() {
		return new GoodServiceImpl();
	}

	@Bean
	@Scope("singleton")
	public GoodDao goodDao() { 
		return new GoodDaoImpl();
	}
}