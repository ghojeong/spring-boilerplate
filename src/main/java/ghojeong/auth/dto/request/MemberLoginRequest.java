package ghojeong.auth.dto.request;

import ghojeong.auth.exception.EmptyLoginRequestException;
import ghojeong.user.domain.UserIdentifier;
import ghojeong.user.domain.type.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberLoginRequest(
        @NotNull
        UserType type,
        @NotBlank
        String ssoId,
        @NotBlank
        String password
) {
    public String userName() {
        return new UserIdentifier(
                type,
                ssoId
        ).toString();
    }

    public void validate() {
        if (type == null
                || ssoId == null
                || password == null
                || ssoId.isBlank()
                || password.isBlank()
        ) {
            throw new EmptyLoginRequestException(this);
        }
    }
}
