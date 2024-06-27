package ghojeong.admin.dto.response.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.common.util.DateTimeParser;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


@Builder
public record AdminResponse(
        String email,
        String nickname,
        String type,
        List<String> permission,
        @JsonFormat(pattern = DateTimeParser.DATETIME_PATTERN)
        LocalDateTime createdAt,
        @JsonFormat(pattern = DateTimeParser.DATETIME_PATTERN)
        LocalDateTime updatedAt
) {
    public static AdminResponse of(AdminUser adminUser) {
        return AdminResponse.builder()
                .email(adminUser.getEmail())
                .nickname(adminUser.getNickname())
                .type(adminUser.getTypeName())
                .permission(adminUser.getPermissionName())
                .createdAt(adminUser.getCreatedAt())
                .updatedAt(adminUser.getUpdatedAt())
                .build();
    }
}
