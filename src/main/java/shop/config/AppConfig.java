package shop.config;

import shop.model.VerificationRequest;
import shop.model.impl.VerificationRequestImpl;
import shop.model.CustomerUpdate;
import shop.model.impl.CustomerUpdateImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public VerificationRequest verificationRequest() {
		return new VerificationRequestImpl();
	}

	@Bean
	public CustomerUpdate customerControll() {
		return CustomerUpdateImpl.newInstance();
	}

}