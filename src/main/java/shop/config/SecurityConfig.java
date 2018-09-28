package shop.config;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource(value = {"classpath:verification.properties"})
@PropertySource(value = {"classpath:authentication.properties"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment enviroment;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        // создаем пользователя в heap'е JVM
        auth.inMemoryAuthentication()
                .withUser(enviroment.getRequiredProperty("authentication.username"))
                .password(enviroment.getRequiredProperty("authentication.password"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .fullyAuthenticated();

        // настройка аутентификации (формы логирования)
        http.formLogin()
                .defaultSuccessUrl("/admin")
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error")
                .permitAll();

        // настройка логаута
        http.logout()
                .permitAll() // разрешаем делать логаут всем
                .logoutUrl("/logout") // указываем URL логаута
                .logoutSuccessUrl("/login?logout") // указываем URL при удачном логауте
                .invalidateHttpSession(true); // делаем не валидной текущую сессию

        // настройка защиты от csrf атак
        http.csrf()
                .disable();

        // добавляем дополнительный фильтр для проверки простого API-ключа
        http.addFilterBefore(tokenFilter(), BasicAuthenticationFilter.class);
    }

    @Bean("tokenFilter")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Filter tokenFilter() {
        return new TokenAuthenticationFilter(enviroment.getRequiredProperty("verification.key"))
                .setURL("/goods/uploadimg/**")
                .setURL("/goods/clearimg/**")
                .setURL("/goods/update")
                .setURL("/goods/updatelist")
                .setURL("/goods/delete/**")                
                .setURL("/customers/list")
                .setURL("/customers/update")
                .setURL("/customers/updatelist")
                .setURL("/customers/delete/**");
    }

}
