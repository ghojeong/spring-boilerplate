package ghojeong.user.repository;

import ghojeong.user.domain.entity.UserWithTags;
import ghojeong.user.domain.type.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserWithTagsRepository extends JpaRepository<UserWithTags, Long> {
    @Query("SELECT u FROM UserWithTags u "
            + " LEFT JOIN FETCH u.tags "
            + " WHERE u.type = :type "
            + " AND u.ssoId = :ssoId "
            + " AND u.deletedAt IS NULL ")
    List<UserWithTags> findByTypeAndSsoId(
            @Param("type") UserType type,
            @Param("ssoId") String ssoId
    );

    @Query("SELECT u FROM UserWithTags u "
            + " LEFT JOIN FETCH u.tags "
            + " WHERE u.userSeq = :userSeq "
            + " AND u.deletedAt IS NULL ")
    Optional<UserWithTags> findBySeq(@Param("userSeq") Long userSeq);
}
