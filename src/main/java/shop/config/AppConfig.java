package shop.config;

import shop.model.VerificationRequest;
import shop.model.impl.VerificationRequestImpl;
import shop.model.CustomerUpdate;
import shop.model.impl.CustomerUpdateImpl;
import shop.model.CustomerAuth;
import shop.model.impl.CustomerAuthImpl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = {"classpath:database.properties"})
public class AppConfig {
	@Autowired
	private Environment enviroment;

	@Bean
    public DriverManagerDataSource dataSource() {
		Properties properties = new Properties();
		properties.setProperty("user", enviroment.getRequiredProperty("mysql.user"));
		properties.setProperty("password", enviroment.getRequiredProperty("mysql.password"));
		properties.setProperty("useUnicode", enviroment.getRequiredProperty("mysql.useUnicode"));
		properties.setProperty("characterEncoding", enviroment.getRequiredProperty("mysql.characterEncoding"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(enviroment.getRequiredProperty("mysql.url"));
		dataSource.setConnectionProperties(properties);
        return dataSource;
    }

	@Bean
	public VerificationRequest verificationRequest() {
		return new VerificationRequestImpl();
	}

	@Bean
	public CustomerUpdate customerControll() {
		return new CustomerUpdateImpl();
	}

	@Bean
	public CustomerAuth customerAuth() {
		return new CustomerAuthImpl();
	}

}