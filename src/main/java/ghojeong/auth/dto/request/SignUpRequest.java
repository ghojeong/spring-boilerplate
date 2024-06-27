package ghojeong.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ghojeong.auth.util.SHA256Util;
import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.type.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;
import static ghojeong.common.util.DateTimeParser.TIME_PATTERN;

@Builder
public record SignUpRequest(
        @NotNull
        UserType type,
        @NotBlank
        String ssoId,
        String password,
        @Email
        String email,
        String nickname,
        String image,
        String biography,

        @JsonFormat(pattern = TIME_PATTERN)
        LocalTime morningReminderTime,
        @JsonFormat(pattern = TIME_PATTERN)
        LocalTime eveningReminderTime,
        Boolean isMorningReminderActive,
        Boolean isEveningReminderActive,
        Boolean isPrivateAccount,
        @JsonFormat(pattern = DATETIME_PATTERN)
        LocalDateTime offlineUpdatedAt
) {
    public User toUser() {
        return User.builder()
                .type(type)
                .ssoId(ssoId)
                .password(SHA256Util.encrypt(password))
                .email(email)
                .nickname(nickname)
                .image(image)
                .biography(biography)
                .morningReminderTime(morningReminderTime)
                .eveningReminderTime(eveningReminderTime)
                .isMorningReminderActive(isMorningReminderActive)
                .isEveningReminderActive(isEveningReminderActive)
                .isPrivateAccount(isPrivateAccount)
                .offlineUpdatedAt(offlineUpdatedAt)
                .build();
    }
}
