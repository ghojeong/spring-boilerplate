package ghojeong.admin.domain.entity;

import ghojeong.admin.domain.type.AdminPermissionType;
import ghojeong.admin.domain.type.AdminType;
import ghojeong.admin.exception.AdminUnauthorizedException;
import ghojeong.common.util.DefaultUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "admin_user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_user_seq")
    private Long adminUserSeq;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_type", insertable = false, updatable = false)
    private AdminType type;

    @Column(name = "admin_permission")
    private String permission;

    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;

    @Column(name = "last_activity_at", insertable = false)
    private LocalDateTime lastActivityAt;

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

    public String getTypeName() {
        return type.name();
    }

    public boolean isAvailable() {
        return !DefaultUtil.getIsDeleted(deletedAt);
    }

    public List<String> getPermissionName() {
        return getPermission().stream()
                .map(AdminPermissionType::name)
                .toList();
    }

    public List<AdminPermissionType> getPermission() {
        if (type.equals(AdminType.SUPER_ADMIN)) {
            return List.of(AdminPermissionType.values());
        }
        return AdminPermissionType.toList(this.permission);
    }

    public AdminUser setPermission(List<AdminPermissionType> permission) {
        if (
                permission.contains(AdminPermissionType.ROLE_SUPER)
                        && !type.equals(AdminType.SUPER_ADMIN)
        ) {
            throw new AdminUnauthorizedException("Super Admin 이 아니면 루트 권한을 가질 수 없습니다.");
        }
        this.permission = AdminPermissionType.toString(permission);
        return this;
    }

    public AdminUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminUser setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public AdminUser delete() {
        this.deletedAt = LocalDateTime.now();
        return this;
    }

    public AdminUser refreshLastActivity() {
        this.lastActivityAt = LocalDateTime.now();
        return this;
    }
}
