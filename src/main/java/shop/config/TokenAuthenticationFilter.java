package shop.config;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import static java.util.Optional.ofNullable;

public class TokenAuthenticationFilter extends GenericFilterBean {

    final private static String PARAMETER_NAME;
    private List<RequestMatcher> matches;
    private final String key;
    
    static {
        PARAMETER_NAME = "key";
    }
    
    {
        matches = new LinkedList<>();
    }
    
    private TokenAuthenticationFilter() {
        throw new AssertionError("No instances without key!");
    }

    public TokenAuthenticationFilter(String key) {
        this.key = key;
    }
    
    public TokenAuthenticationFilter setURL(String url) {
        matches.add(new AntPathRequestMatcher(url));
        return this;
    }

    protected boolean requiresAuthentication(HttpServletRequest request) {
        for (RequestMatcher matcher : matches) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (requiresAuthentication((HttpServletRequest) request)) {
            if (!ofNullable(request.getParameter(PARAMETER_NAME))
                    .map(key -> this.key.equals(key))
                    .orElse(Boolean.FALSE)) {
                HttpServletResponse httpresponse = (HttpServletResponse) response;
                httpresponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
