package ghojeong.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AdminLoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {}
