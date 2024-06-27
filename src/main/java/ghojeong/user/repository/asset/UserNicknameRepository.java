package ghojeong.user.repository.asset;

import ghojeong.user.domain.entity.UserNickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNicknameRepository extends JpaRepository<UserNickname, Long> {
    @Query(value = "SELECT nickname FROM user_nickname ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String selectRandomName();

    List<UserNickname> findAllByNickname(String nickname);
}
