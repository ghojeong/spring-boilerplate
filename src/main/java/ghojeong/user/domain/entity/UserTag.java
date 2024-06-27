package ghojeong.user.domain.entity;

import ghojeong.user.domain.pk.UserTagPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "user_tag")
@IdClass(UserTagPK.class)
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag {
    @Id
    @Column(name = "user_tag", updatable = false)
    private String userTag;

    @Id
    @Column(name = "user_seq", updatable = false)
    private Long userSeq;

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

    public static List<UserTag> toList(List<String> tags, Long userSeq) {
        return tags.stream().map(
                tag -> UserTag.builder()
                        .userTag(tag)
                        .userSeq(userSeq)
                        .deletedAt(null)
                        .build()
        ).toList();
    }
}
