package ghojeong.image.domain;

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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "image")
@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @Column(name = "image", updatable = false)
    private String image;

    @CreatedDate
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

    public Image(String image) {
        this.image = image;
    }

    public static Image oldImage(String image) {
        return Image.builder()
                .image(image)
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static Image newImage(String image) {
        return Image.builder()
                .image(image)
                .deletedAt(null)
                .build();
    }
}
