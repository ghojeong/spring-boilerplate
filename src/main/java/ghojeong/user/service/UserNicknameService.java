package ghojeong.user.service;

import ghojeong.user.domain.entity.UserNickname;
import ghojeong.user.dto.request.CreateNicknamesRequest;
import ghojeong.user.repository.asset.UserNicknameRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNicknameService {
    private final UserNicknameRepository userNicknameRepository;

    public void saveAll(CreateNicknamesRequest request) {
        request.toEntity().forEach(this::save);
    }

    private void save(UserNickname nickname) {
        if (
                userNicknameRepository.findAllByNickname(
                        nickname.getNickname()
                ).isEmpty()
        ) {
            userNicknameRepository.save(nickname);
        }
    }
}
