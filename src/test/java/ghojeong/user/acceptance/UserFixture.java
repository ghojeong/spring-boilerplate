package ghojeong.user.acceptance;

import ghojeong.common.CommonFixture;
import ghojeong.user.domain.entity.User;
import ghojeong.user.dto.request.UpdateUserRequest;

import java.util.List;

import static ghojeong.common.util.DateTimeParser.toLocalDateTime;
import static ghojeong.common.util.DateTimeParser.toLocalTime;

public final class UserFixture extends CommonFixture {

    private UserFixture() {}

    public static User createUpdatedUser() {
        User user = createUser();
        return User.builder()
                .type(user.getType())
                .ssoId(user.getSsoId())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname("Pyro_2")
                .image("profile_icon_2")
                .biography("explanation2 about personal biography")
                .morningReminderTime(toLocalTime("07:12"))
                .eveningReminderTime(toLocalTime("22:58"))
                .isMorningReminderActive(false)
                .isEveningReminderActive(false)
                .isPrivateAccount(true)
                .offlineUpdatedAt(toLocalDateTime("2023-04-14 09:10:10"))
                .build();
    }

    public static UpdateUserRequest createUpdateRequest() {
        return createUpdateRequest(createUpdatedUser());
    }

    private static UpdateUserRequest createUpdateRequest(User user) {
        return UpdateUserRequest.builder()
                .nickname(user.getNickname())
                .image(user.getImage())
                .biography(user.getBiography())
                .tags(List.of("Tech", "Music"))
                .morningReminderTime(user.getMorningReminderTime())
                .eveningReminderTime(user.getEveningReminderTime())
                .isMorningReminderActive(user.getIsMorningReminderActive())
                .isEveningReminderActive(user.getIsEveningReminderActive())
                .isPrivateAccount(user.getIsPrivateAccount())
                .offlineUpdatedAt(user.getOfflineUpdatedAt())
                .build();
    }
}
