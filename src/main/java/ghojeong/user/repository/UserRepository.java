package ghojeong.user.repository;

import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.type.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u "
            + " WHERE u.ssoId = :ssoId "
            + " AND u.type = :type "
            + " AND u.deletedAt IS NULL ")
    List<User> findByTypeAndSsoId(
            @Param("type") UserType type,
            @Param("ssoId") String ssoId
    );
}
