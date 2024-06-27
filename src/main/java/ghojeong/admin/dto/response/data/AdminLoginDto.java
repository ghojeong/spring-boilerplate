package ghojeong.admin.dto.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;

@Builder
public record AdminLoginDto(
        String email,
        String nickname,
        String accessToken,
        @JsonFormat(pattern = DATETIME_PATTERN)
        LocalDateTime accessTokenExpiredAt
) {}
