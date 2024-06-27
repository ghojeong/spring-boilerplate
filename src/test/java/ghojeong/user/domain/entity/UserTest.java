package ghojeong.user.domain.entity;

import ghojeong.user.acceptance.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

final class UserTest {

    private User user;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        this.user = UserFixture.createUser();
        this.updatedUser = UserFixture.createUpdatedUser();
    }

    @DisplayName("User 의 정보를 변경")
    @Test
    void overwrite() {
        User user = this.user.updateBy(updatedUser);
        testCompare(user, updatedUser);
    }

    private void testCompare(User user, User updatedUser) {
        assertAll(
                () -> assertThat(user.getType()).isEqualTo(updatedUser.getType()),
                () -> assertThat(user.getType()).isEqualTo(updatedUser.getType()),
                () -> assertThat(user.getSsoId()).isEqualTo(updatedUser.getSsoId()),
                () -> assertThat(user.getPassword()).isEqualTo(updatedUser.getPassword()),
                () -> assertThat(user.getEmail()).isEqualTo(updatedUser.getEmail()),
                () -> assertThat(user.getNickname()).isEqualTo(updatedUser.getNickname()),
                () -> assertThat(user.getImage()).isEqualTo(updatedUser.getImage()),
                () -> assertThat(user.getBiography()).isEqualTo(updatedUser.getBiography())
        );
    }

    @DisplayName("User 를 soft delete 함")
    @Test
    void delete() {
        user.delete();
        assertAll(
                () -> assertThat(user.getIsDeleted()).isTrue(),
                () -> assertThat(user.getDeletedAt()).isNotNull()
        );
    }
}
