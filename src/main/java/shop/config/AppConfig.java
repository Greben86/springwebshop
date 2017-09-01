package shop.config;

import shop.model.VerificationRequest;
import shop.model.impl.VerificationRequestImpl;
import shop.model.CustomerUpdate;
import shop.model.impl.CustomerUpdateImpl;
import shop.model.CustomerAuth;
import shop.model.impl.CustomerAuthImpl;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {

	@Bean
    public DriverManagerDataSource dataSource() {
		Properties properties = new Properties();
		properties.setProperty("user","webapp");
		properties.setProperty("password","ros1nf0rm");
		properties.setProperty("useUnicode","true");
		properties.setProperty("characterEncoding","cp1251");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/webshop");
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