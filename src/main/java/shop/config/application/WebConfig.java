package shop.config.application;

import javax.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration; 
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
@EnableWebMvc
// @EnableAutoConfiguration
@ComponentScan({"shop.config", "shop.controller", "shop.model", "shop.service"})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html; charset=utf-8");
        return viewResolver; 
    }

    // @Bean(name = "multipartResolver")
    // public CommonsMultipartResolver multipartResolver() {
    //     CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    //     multipartResolver.setMaxUploadSize(100000);
    //     return multipartResolver;
    // }
    // @Order(0)
    // public MultipartFilter multipartFilter() {
    //     MultipartFilter multipartFilter = new MultipartFilter();
    //     multipartFilter.setMultipartResolverBeanName("multipartResolver");
    //     return multipartFilter;
    // }

    // @Bean
    // public MultipartConfigElement multipartConfigElement() {
    //     MultipartConfigFactory factory = new MultipartConfigFactory();
    //     factory.setMaxFileSize("128KB");
    //     factory.setMaxRequestSize("128KB");
    //     return factory.createMultipartConfig();
    // }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/views/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/views/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/views/images/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/views/resources/");
    }
}