package ghojeong.user.repository;

import ghojeong.user.domain.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    @Modifying
    @Query("UPDATE UserTag t "
            + " SET t.deletedAt = NOW() "
            + " WHERE t.userSeq = :userSeq ")
    void deleteAllByUserSeq(@Param("userSeq") Long userSeq);
}
