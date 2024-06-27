package ghojeong.user.domain;

import ghojeong.user.acceptance.UserFixture;
import ghojeong.user.domain.type.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

final class UserIdentifierTest {

    @DisplayName("String 을 UserIdentifier 로 파싱할 수 있다.")
    @Test
    void parseAuthName() {
        UserIdentifier identifier = UserIdentifier.parseAuthName("facebook__ssoId");
        assertAll(
                () -> assertThat(identifier.userType()).isEqualTo(UserType.FACEBOOK),
                () -> assertThat(identifier.ssoId()).isEqualTo("ssoId")
        );
    }

    @DisplayName("UserIdentifier 를 String 으로 만들 수 있다.")
    @Test
    void testToString() {
        assertThat(
                UserIdentifier.of(
                        UserFixture.createUser()
                ).toString()
        ).isEqualTo("GOOGLE__Single_Sign_On_ID");
    }
}
