package shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
    }

}
