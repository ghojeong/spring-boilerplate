package ghojeong.admin.dto.request;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.domain.type.AdminType;
import ghojeong.auth.util.SHA256Util;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAdminRequest(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String nickname
) {
    public AdminUser toEntity() {
        return AdminUser.builder()
                .email(email)
                .password(SHA256Util.encrypt(password))
                .nickname(nickname)
                .type(AdminType.ADMIN)
                .deletedAt(null)
                .build();
    }
}
