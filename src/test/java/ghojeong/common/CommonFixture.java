package ghojeong.common;

import ghojeong.admin.dto.request.AdminLoginRequest;
import ghojeong.auth.dto.request.LogInRequest;
import ghojeong.auth.dto.request.SignUpRequest;
import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.type.UserType;

import static ghojeong.common.util.DateTimeParser.toLocalDateTime;
import static ghojeong.common.util.DateTimeParser.toLocalTime;

public abstract class CommonFixture {
    public static User createUser() {
        return User.builder()
                .type(UserType.GOOGLE)
                .ssoId("Single_Sign_On_ID")
                .password("P@ssw0rd")
                .email("listen@ghojeong.com")
                .nickname("User Nickname")
                .image("profile_icon_1")
                .biography("explanation about personal biography")
                .morningReminderTime(toLocalTime("08:00"))
                .eveningReminderTime(toLocalTime("22:00"))
                .isMorningReminderActive(true)
                .isEveningReminderActive(true)
                .isPrivateAccount(false)
                .offlineUpdatedAt(toLocalDateTime("2023-04-10 09:10:10"))
                .build();
    }

    public static LogInRequest createLogInRequest() {
        return createLogInRequest(createUser());
    }

    private static LogInRequest createLogInRequest(User user) {
        return LogInRequest.builder()
                .type(user.getType())
                .ssoId(user.getSsoId())
                .build();
    }

    public static SignUpRequest createSignUpRequest() {
        return createSignUpRequest(createUser());
    }

    private static SignUpRequest createSignUpRequest(User user) {
        return SignUpRequest.builder()
                .type(user.getType())
                .ssoId(user.getSsoId())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .biography(user.getBiography())
                .morningReminderTime(user.getMorningReminderTime())
                .eveningReminderTime(user.getEveningReminderTime())
                .isMorningReminderActive(user.getIsMorningReminderActive())
                .isEveningReminderActive(user.getIsEveningReminderActive())
                .isPrivateAccount(user.getIsPrivateAccount())
                .offlineUpdatedAt(user.getOfflineUpdatedAt())
                .build();
    }

    static AdminLoginRequest createSuperAdminLoginRequest() {
        return AdminLoginRequest.builder()
                .email("superAdmin@ghojeong.com")
                .password("Dream77!!")
                .build();
    }
}
