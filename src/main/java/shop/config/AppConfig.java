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
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = {"classpath:database.properties"})
@PropertySource(value = {"classpath:verification.properties"})
public class AppConfig {
	@Autowired
	private Environment enviroment;

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
	public CustomerService customerService() {
		return new CustomerServiceImpl();
	}

	@Bean
	@Scope("singleton")
	public CustomerDao customerDao() {
		return new CustomerDaoImpl();
	}

}