package ghojeong.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateAdminNicknameRequest(
        @NotBlank
        String nickname
) {}
