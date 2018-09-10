package shop.config;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
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

//        http.addFilterBefore(tokenAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean(name = "tokenAuthenticationFilter")
//    public Filter tokenAuthenticationFilter() {
////        TokenAuthenticationFilter filter = new TokenAuthenticationFilter("/goods/**");
////        filter.setAuthenticationManager(new TokenAuthenticationManager());
////        filter.setUserDetailsService(userDetailsService());
//        return new TokenAuthenticationFilter("/goods/**");
//    }

}
