package ghojeong.admin.repository;

import ghojeong.admin.domain.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminUser, String> {
    @Query("SELECT u FROM AdminUser u WHERE u.email = :email")
    Optional<AdminUser> findByEmail(@Param("email") String email);
}
