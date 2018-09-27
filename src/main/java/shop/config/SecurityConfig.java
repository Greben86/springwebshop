package shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource(value = {"classpath:verification.properties"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private Environment enviroment;
    
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        // создаем пользователя в heap'е JVM
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("qwerty123")
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

        http.addFilterBefore(new TokenAuthenticationFilter(enviroment.getRequiredProperty("verification.key"))
                .setURL("/goods/**")
                .setURL("/customers/**"),
                BasicAuthenticationFilter.class);
    }

}
