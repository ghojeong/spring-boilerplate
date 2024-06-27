package ghojeong.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.entity.UserTag;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;
import static ghojeong.common.util.DateTimeParser.TIME_PATTERN;
import static ghojeong.common.util.ListUtil.getOrEmptyList;

@Builder
public record UpdateUserRequest(
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
        LocalDateTime offlineUpdatedAt
) {
    public User toUser() {
        return User.builder()
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

    public List<UserTag> toUserTags(Long userSeq) {
        return UserTag.toList(getOrEmptyList(tags), userSeq);
    }
}
