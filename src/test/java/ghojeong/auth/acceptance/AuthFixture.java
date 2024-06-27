package ghojeong.auth.acceptance;

import ghojeong.auth.dto.request.MemberLoginRequest;
import ghojeong.common.CommonFixture;
import ghojeong.user.domain.entity.User;

import static ghojeong.user.domain.type.UserType.GOOGLE;

public final class AuthFixture extends CommonFixture {
    private AuthFixture() {}

    static MemberLoginRequest createLoginRequest() {
        User user = createUser();
        return MemberLoginRequest.builder()
                .type(user.getType())
                .ssoId(user.getSsoId())
                .password(user.getPassword())
                .build();
    }

    static MemberLoginRequest createEmptyLoginRequest() {
        return MemberLoginRequest.builder()
                .type(GOOGLE)
                .ssoId("")
                .password("")
                .build();
    }

    static MemberLoginRequest createInvalidPasswordLoginRequest() {
        User user = createUser();
        return MemberLoginRequest.builder()
                .type(user.getType())
                .ssoId(user.getSsoId())
                .password("INVALID_P@ssw0rd")
                .build();
    }
}
