package shop.config;

import shop.model.VerificationRequest;
import shop.model.impl.VerificationRequestImpl;
import shop.model.CustomerControll;
import shop.model.impl.CustomerControllImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public VerificationRequest verificationRequest() {
		return new VerificationRequestImpl();
	}

	@Bean
	public CustomerControll customerControll() {
		return CustomerControllImpl.newInstance();
	}

}