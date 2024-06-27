package ghojeong.auth.dto.request;

import ghojeong.user.domain.type.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LogInRequest(
        @NotNull
        UserType type,
        @NotBlank
        String ssoId
) {}
