package shop.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import static java.util.Optional.ofNullable;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {
        System.out.println("key: " + request.getParameter("key"));
        return ofNullable(request.getParameter("key"))
                .map(key -> {
                    TokenAuthentication authentication = new TokenAuthentication();
                    authentication.setAuthenticated(true);
                    return authentication;
                })
                .orElseGet(TokenAuthentication::new);
    }

}
