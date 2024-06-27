package ghojeong.user.repository.asset;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRandomRepository {
    private final static Random random = new Random();
    private final UserNicknameRepository userNicknameRepository;

    public String generateImage() {
        final String IMAGE_FORMAT = "profile_icon_%d";
        final int MIN_IMAGE_NUMBER = 1;
        final int MAX_IMAGE_NUMBER = 48;
        return String.format(
                IMAGE_FORMAT,
                MIN_IMAGE_NUMBER + random.nextInt(MAX_IMAGE_NUMBER)
        );
    }

    public String generateNickname() {
        final String NICKNAME_FORMAT = "%s_%s";
        return String.format(
                NICKNAME_FORMAT,
                userNicknameRepository.selectRandomName(),
                generateNumber()
        );
    }

    private String generateNumber() {
        final String NUMBER_FORMAT = "%04d";
        final int MAX_NUMBER = 10000;
        return String.format(NUMBER_FORMAT, random.nextInt(MAX_NUMBER));
    }
}
