package ghojeong.auth.filter;

import ghojeong.auth.dto.request.MemberLoginRequest;
import ghojeong.auth.util.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

// NOTE: org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
@Component
public class PasswordAuthFilter extends AbstractAuthenticationProcessingFilter {
    private static final String HTTP_METHOD = "POST";
    private static final String HTTP_URL = "/login";
    private static final AntPathRequestMatcher requestMatcher =
            new AntPathRequestMatcher(HTTP_URL, HTTP_METHOD);
    private final JsonMapper jsonMapper;

    @SuppressWarnings("this-escape")
    protected PasswordAuthFilter(
            JsonMapper jsonMapper,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler,
            AuthenticationManager authenticationManager
    ) {
        super(requestMatcher);
        this.jsonMapper = jsonMapper;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {
        return getAuthenticationManager().authenticate(
                createAuthToken(request)
        );
    }

    private Authentication createAuthToken(
            HttpServletRequest request
    ) throws IOException {
        MemberLoginRequest memberLoginRequest = jsonMapper
                .readRequest(request, MemberLoginRequest.class);
        memberLoginRequest.validate();
        String authName = memberLoginRequest.userName();
        String password = memberLoginRequest.password();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authName, password);
        authToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return authToken;
    }
}
