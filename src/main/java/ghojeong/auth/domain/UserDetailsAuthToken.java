package ghojeong.auth.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsAuthToken extends UsernamePasswordAuthenticationToken {
    private final UserDetails userDetails;

    public UserDetailsAuthToken(UserDetails userDetails) {
        super(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        this.userDetails = userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }
}
