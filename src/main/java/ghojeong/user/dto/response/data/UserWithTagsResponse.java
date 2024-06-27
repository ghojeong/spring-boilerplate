package ghojeong.user.dto.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import ghojeong.common.util.DateTimeParser;
import ghojeong.user.domain.entity.UserWithTags;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
public record UserWithTagsResponse(
        Long seq,
        Long userSeq,
        String type,
        String ssoId,
        String email,
        String nickname,
        String image,
        String biography,
        List<String> tags,
        @JsonFormat(pattern = DateTimeParser.TIME_PATTERN)
        LocalTime morningReminderTime,
        @JsonFormat(pattern = DateTimeParser.TIME_PATTERN)
        LocalTime eveningReminderTime,
        Boolean isMorningReminderActive,
        Boolean isEveningReminderActive,
        Boolean isPrivateAccount,
        @JsonFormat(pattern = DateTimeParser.DATETIME_PATTERN)
        LocalDateTime offlineUpdatedAt
) {
    public static UserWithTagsResponse of(UserWithTags user) {
        return UserWithTagsResponse.builder()
                .seq(user.getUserSeq())
                .userSeq(user.getUserSeq())
                .type(user.getType().name())
                .ssoId(user.getSsoId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .biography(user.getBiography())
                .tags(user.getTags())
                .morningReminderTime(user.getMorningReminderTime())
                .eveningReminderTime(user.getEveningReminderTime())
                .isMorningReminderActive(user.getIsMorningReminderActive())
                .isEveningReminderActive(user.getIsEveningReminderActive())
                .isPrivateAccount(user.getIsPrivateAccount())
                .offlineUpdatedAt(user.getOfflineUpdatedAt())
                .build();
    }
}
