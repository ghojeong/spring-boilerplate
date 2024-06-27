package ghojeong.auth.domain;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;
import java.util.Collections;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final String jwt;

    public JwtAuthToken(String jwt) {
        super(Collections.emptyList());
        this.jwt = jwt;
    }

    @Override
    public String getName() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
