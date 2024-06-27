package ghojeong.user.domain.entity;

import ghojeong.common.domain.YNConverter;
import ghojeong.common.util.DateTimeParser;
import ghojeong.common.util.DefaultUtil;
import ghojeong.user.domain.type.UserType;
import ghojeong.user.exception.InvalidUserException;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long userSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", updatable = false)
    private UserType type;

    @Column(name = "sso_id", updatable = false)
    private String ssoId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "image")
    private String image;

    @Column(name = "biography")
    private String biography;

    @Column(name = "morning_reminder_time")
    private LocalTime morningReminderTime;

    @Column(name = "evening_reminder_time")
    private LocalTime eveningReminderTime;

    @Convert(converter = YNConverter.class)
    @Column(name = "morning_reminder_active_yn")
    private Boolean isMorningReminderActive;

    @Convert(converter = YNConverter.class)
    @Column(name = "evening_reminder_active_yn")
    private Boolean isEveningReminderActive;

    @Convert(converter = YNConverter.class)
    @Column(name = "private_account_yn")
    private Boolean isPrivateAccount;

    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;
    @Column(name = "offline_updated_at")
    private LocalDateTime offlineUpdatedAt;

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

    public List<String> getRoles() {
        return List.of("ROLE_MEMBER");
    }

    public boolean getIsDeleted() {
        return DefaultUtil.getIsDeleted(deletedAt);
    }

    public User updateBy(User user) {
        if (offlineUpdatedAt != null
                && user.offlineUpdatedAt != null
                && offlineUpdatedAt.isAfter(user.offlineUpdatedAt)
        ) {
            return this;
        }
        if (user.nickname != null) {
            this.nickname = user.nickname;
        }
        if (user.image != null) {
            this.image = user.image;
        }
        if (user.biography != null) {
            this.biography = user.biography;
        }
        if (user.morningReminderTime != null) {
            this.morningReminderTime = user.morningReminderTime;
        }
        if (user.eveningReminderTime != null) {
            this.eveningReminderTime = user.eveningReminderTime;
        }
        if (user.isPrivateAccount != null) {
            this.isPrivateAccount = user.isPrivateAccount;
        }
        if (user.offlineUpdatedAt != null) {
            this.offlineUpdatedAt = user.offlineUpdatedAt;
        }
        return this;
    }

    public User setDefault(
            Supplier<String> nicknameSupplier,
            Supplier<String> imageSupplier
    ) {
        if (email != null && email.contains("@")
                && (nickname == null || nickname.isBlank())
        ) {
            nickname = email.split("@")[0];
        }
        if (nickname == null || nickname.isBlank()) {
            nickname = nicknameSupplier.get();
        }

        if (image == null) {
            image = imageSupplier.get();
        }

        if (morningReminderTime == null) {
            morningReminderTime = DateTimeParser.toLocalTime("08:00");
        }
        if (eveningReminderTime == null) {
            eveningReminderTime = DateTimeParser.toLocalTime("22:00");
        }
        if (isMorningReminderActive == null) {
            isMorningReminderActive = true;
        }
        if (isEveningReminderActive == null) {
            isEveningReminderActive = true;
        }
        if (isPrivateAccount == null) {
            isPrivateAccount = false;
        }
        return this;
    }

    public void validate() {
        if (nickname == null && email == null) {
            throw new InvalidUserException();
        }
    }

    public User delete() {
        this.deletedAt = LocalDateTime.now();
        return this;
    }

    public User refreshLastActivity() {
        this.lastActivityAt = LocalDateTime.now();
        return this;
    }
}
