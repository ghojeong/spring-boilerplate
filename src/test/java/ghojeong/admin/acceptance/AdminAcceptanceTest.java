package ghojeong.admin.acceptance;

import ghojeong.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class AdminAcceptanceTest extends AcceptanceTest {

    @DisplayName("어드민 생성 성공")
    @Test
    void createAdmin() {
        assertThat(
                AdminSteps.createAdmin()
                        .jsonPath()
                        .getString("data.email")
        ).isNotBlank();
    }

    @DisplayName("어드민 삭제 성공")
    @Test
    void deleteAdmin() {
        assertThat(
                AdminSteps.deleteAdmin()
                        .jsonPath()
                        .getString("data")
        ).isNotNull();
    }

    @DisplayName("어드민 로그인 성공")
    @Test
    void login() {
        assertThat(
                AdminSteps.loginAdmin()
                        .jsonPath()
                        .getString("data.accessToken")
        ).isNotBlank();
    }

    @DisplayName("어드민 닉네임 수정 성공")
    @Test
    void updateNickname() {
        String nickname = "Changed Nickname";
        assertThat(
                AdminSteps.updateAdminNickname(nickname)
                        .jsonPath()
                        .getString("data.nickname")
        ).isEqualTo(nickname);
    }

    @DisplayName("어드민 비밀번호 수정 성공")
    @Test
    void updatePassword() {
        String password = "Changed Password";
        assertThat(
                AdminSteps.updateAdminPassword(password)
                        .jsonPath()
                        .getString("data")
        ).isNotNull();
    }

    @DisplayName("어드민 권한 수정 성공")
    @Test
    void updatePermission() {
        List<String> permission = List.of(
                "ROLE_USER",
                "ROLE_FEED"
        );
        assertThat(
                AdminSteps.updateAdminPermission(permission)
                        .statusCode(HttpStatus.OK.value())
                        .extract().jsonPath()
                        .getList("data.permission")
        ).isEqualTo(permission);
    }

    @DisplayName("어드민 권한 수정 실패")
    @Test
    void updatePermissionFail() {
        List<String> permission = List.of(
                "ROLE_USER",
                "ROLE_SUPER"
        );
        assertThat(
                AdminSteps.updateAdminPermission(permission)
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract().jsonPath()
                        .getString("message")
        ).isEqualTo("Super Admin 이 아니면 루트 권한을 가질 수 없습니다.");
    }
}
