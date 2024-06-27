package ghojeong.auth.service;

import ghojeong.auth.domain.UserDetailsAuthToken;
import ghojeong.auth.exception.InvalidPasswordException;
import ghojeong.auth.util.SHA256Util;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PasswordAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public PasswordAuthProvider(MemberDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authName = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(authName);
        checkPassword(password, userDetails.getPassword());
        return new UserDetailsAuthToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }

    private void checkPassword(String authPassword, String dbPassword) {
        if (authPassword == null && dbPassword == null) {
            return;
        }
        if (authPassword == null || !SHA256Util.comparePassword(authPassword, dbPassword)) {
            throw new InvalidPasswordException(authPassword);
        }
    }
}
