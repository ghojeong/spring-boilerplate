package ghojeong.user.domain.entity;


import ghojeong.common.domain.YNConverter;
import ghojeong.user.domain.type.UserType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ghojeong.common.util.ListUtil.getOrEmptyList;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWithTags {
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

    @OneToMany(cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.JOIN)
    @SQLRestriction("deleted_at IS NULL")
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private List<UserTag> tags;

    public static UserWithTags of(User user) {
        return UserWithTags.builder()
                .userSeq(user.getUserSeq())
                .type(user.getType())
                .ssoId(user.getSsoId())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .biography(user.getBiography())
                .morningReminderTime(user.getMorningReminderTime())
                .eveningReminderTime(user.getEveningReminderTime())
                .isPrivateAccount(user.getIsPrivateAccount())
                .deletedAt(user.getDeletedAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .creator(user.getCreator())
                .updater(user.getUpdater())
                .tags(Collections.emptyList())
                .build();
    }

    public List<String> getTags() {
        return getOrEmptyList(tags).stream()
                .map(UserTag::getUserTag)
                .toList();
    }

    public String getTypeName() {
        return type.name();
    }
}
