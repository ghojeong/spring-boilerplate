package ghojeong.user.service;

import ghojeong.auth.dto.request.LogInRequest;
import ghojeong.auth.exception.MemberLogInFailedException;
import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.entity.UserWithTags;
import ghojeong.user.domain.type.UserType;
import ghojeong.user.repository.UserRepository;
import ghojeong.user.repository.UserWithTagsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserWithTagsRepository userWithTagsRepository;

    public User findUser(LogInRequest request) {
        return findUser(
                request.type(), request.ssoId()
        ).stream().findFirst().orElseThrow(MemberLogInFailedException::new);
    }

    public UserWithTags findUserWithTags(User user) {
        return findUserWithTags(
                user.getType(), user.getSsoId()
        ).orElse(UserWithTags.of(user));
    }

    public Optional<UserWithTags> findUserWithTags(UserType type, String ssoId) {
        return userWithTagsRepository.findByTypeAndSsoId(
                type, ssoId
        ).stream().findFirst();
    }

    public Optional<User> findUser(UserType type, String ssoId) {
        return userRepository.findByTypeAndSsoId(
                type, ssoId
        ).stream().findFirst();
    }
}
