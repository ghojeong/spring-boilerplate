package ghojeong.auth.dto.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;
import static ghojeong.common.util.DateTimeParser.TIME_PATTERN;

@Builder
public record MemberLoginDto(
        Long seq,
        Long userSeq,
        String type,
        String ssoId,
        String email,
        String nickname,
        String image,
        String biography,
        List<String> tags,
        @JsonFormat(pattern = TIME_PATTERN)
        LocalTime morningReminderTime,
        @JsonFormat(pattern = TIME_PATTERN)
        LocalTime eveningReminderTime,
        Boolean isMorningReminderActive,
        Boolean isEveningReminderActive,
        Boolean isPrivateAccount,
        @JsonFormat(pattern = DATETIME_PATTERN)
        LocalDateTime offlineUpdatedAt,
        String accessToken,
        String refreshToken,
        @JsonFormat(pattern = DATETIME_PATTERN)
        LocalDateTime accessTokenExpiredAt,
        @JsonFormat(pattern = DATETIME_PATTERN)
        LocalDateTime refreshTokenExpiredAt
) {}
