package ghojeong.user.service;

import ghojeong.auth.dto.request.SignUpRequest;
import ghojeong.image.service.ImageService;
import ghojeong.user.domain.entity.User;
import ghojeong.user.dto.request.UpdateUserRequest;
import ghojeong.user.dto.response.DeleteUserResponse;
import ghojeong.user.repository.UserRepository;
import ghojeong.user.repository.UserTagRepository;
import ghojeong.user.repository.asset.UserRandomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandService {
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserRandomRepository userRandomRepository;
    private final ImageService imageService;
    private final UserQueryService userQueryService;

    public void updateUser(User authUser, UpdateUserRequest request) {
        imageService.update(authUser.getImage(), request.image());
        final Long userSeq = userRepository.save(
                authUser.updateBy(request.toUser())
        ).getUserSeq();
        userTagRepository.deleteAllByUserSeq(userSeq);
        userTagRepository.saveAll(request.toUserTags(userSeq));
    }

    public DeleteUserResponse deleteUser(User user) {
        userTagRepository.deleteAllByUserSeq(user.getUserSeq());
        return DeleteUserResponse.of(
                userRepository.save(user.delete())
        );
    }

    public User signUp(SignUpRequest request) {
        return createUser(request.toUser());
    }

    private User createUser(User user) {
        user.validate();
        return userQueryService.findUser(
                user.getType(), user.getSsoId()
        ).stream().findFirst().orElseGet(
                () -> createUserWithDefault(user)
        );
    }

    private User createUserWithDefault(User user) {
        return userRepository.save(user.setDefault(
                userRandomRepository::generateNickname,
                userRandomRepository::generateImage
        ));
    }
}
