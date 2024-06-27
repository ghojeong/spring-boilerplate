package ghojeong.auth.service;

import ghojeong.auth.domain.MemberUserDetails;
import ghojeong.auth.exception.AuthMemberNotFoundException;
import ghojeong.user.domain.UserIdentifier;
import ghojeong.user.domain.entity.User;
import ghojeong.user.repository.UserRepository;
import ghojeong.user.service.UserQueryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new MemberUserDetails(userRepository.save(
                findByAuthName(username).refreshLastActivity()
        ));
    }

    private User findByAuthName(String authName) {
        UserIdentifier identifier = UserIdentifier.parseAuthName(authName);
        return userQueryService.findUser(
                        identifier.userType(), identifier.ssoId()
                ).stream().findFirst()
                .orElseThrow(() -> new AuthMemberNotFoundException(authName));
    }
}
