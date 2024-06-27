package ghojeong.auth.service;

import ghojeong.auth.domain.AuthType;
import ghojeong.auth.domain.JwtAuthToken;
import ghojeong.auth.domain.JwtDetails;
import ghojeong.auth.domain.UserDetailsAuthToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAuthProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;
    private final AdminDetailsService adminDetailsService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String jwt = authentication.getName();
        if (jwtService.isInvalidToken(jwt)) {
            return authentication;
        }
        final JwtDetails jwtDetails = jwtService.decodeToken(jwt);
        final UserDetails userDetails = loadUserDetails(jwtDetails);
        return new UserDetailsAuthToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class
                .isAssignableFrom(authentication);
    }

    private UserDetails loadUserDetails(JwtDetails jwtDetails) {
        final String authName = jwtDetails.authName();
        return jwtDetails.type().equals(AuthType.ADMIN)
                ? adminDetailsService.loadUserByUsername(authName)
                : memberDetailsService.loadUserByUsername(authName);
    }
}
