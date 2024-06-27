package ghojeong.auth.acceptance;

import ghojeong.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입 성공")
    @Test
    void signUp() {
        assertThat(
                AuthSteps.signUp()
                        .jsonPath()
                        .getString("data.accessToken")
        ).isNotNull();
    }

    @DisplayName("회원탈퇴 성공")
    @Test
    void deleteMyUser() {
        assertThat(
                AuthSteps.deleteMyUser()
                        .jsonPath()
                        .getString("data.seq")
        ).isNotNull();
    }

    @DisplayName("Log In 성공")
    @Test
    void login() {
        assertThat(
                AuthSteps.login()
                        .jsonPath()
                        .getString("data.accessToken")
        ).isNotNull();
    }

    @DisplayName("Refresh Token 성공")
    @Test
    void refreshToken() {
        assertThat(
                AuthSteps.refreshToken()
                        .jsonPath()
                        .getString("data.accessToken")
        ).isNotNull();
    }

    @DisplayName("비어있는 값으로 Log In 실패")
    @Test
    void emptyLoginFail() {
        AuthSteps.emptyLoginFail();
    }

    @DisplayName("잘못된 비밀번호로 Log In 실패")
    @Test
    void invalidPasswordLoginFail() {
        AuthSteps.invalidPasswordLoginFail();
    }

    @DisplayName("잘못된 토큰으로 토큰 재발급 실패")
    @Test
    void invalidTokenRefreshTokenFail() {
        AuthSteps.refreshTokenFail();
    }

    @DisplayName("가입하지 않은 회원으로 로그인 실패")
    @Test
    void loginFail() {
        AuthSteps.loginFail();
    }

    @DisplayName("가입하지 않은 회원으로 Log In 실패")
    @Test
    void unauthorizedLogInFail() {
        AuthSteps.unauthorizedLogIn();
    }
}
