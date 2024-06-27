package ghojeong.user.acceptance;

import ghojeong.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class UserAcceptanceTest extends AcceptanceTest {

    @DisplayName("내 계정정보 조회 성공")
    @Test
    void fetchMyUser() {
        assertThat(
                UserSteps.fetchUser()
                        .jsonPath()
                        .getString("data.seq")
        ).isNotNull();
    }

    @DisplayName("내 계정정보 수정 성공")
    @Test
    void updateMyUser() {
        assertThat(
                UserSteps.updateUser()
                        .jsonPath()
                        .getString("data.seq")
        ).isNotNull();
    }
}
