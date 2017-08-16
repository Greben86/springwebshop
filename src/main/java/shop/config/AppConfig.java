package shop.config;

import shop.model.AddCustomer;
import shop.model.impl.AddCustomerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public AddCustomer addCustomer() {
		return new AddCustomerImpl();
	}

}