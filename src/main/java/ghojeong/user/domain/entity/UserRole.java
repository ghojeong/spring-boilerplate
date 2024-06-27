package ghojeong.user.domain.entity;

import ghojeong.user.domain.pk.UserRolePK;
import ghojeong.user.domain.type.UserRoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_role")
@IdClass(UserRolePK.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", updatable = false)
    private UserRoleType role;

    @Id
    @Column(name = "user_seq", updatable = false)
    private Long userSeq;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "creator", updatable = false)
    private String creator;

    @LastModifiedBy
    @Column(name = "updater")
    private String updater;

    public UserRole(UserRoleType role, Long userSeq) {
        this.role = role;
        this.userSeq = userSeq;
    }

    public String getRole() {
        return role.name();
    }

    public boolean match(UserRoleType roleType) {
        return this.role.equals(roleType);
    }
}
