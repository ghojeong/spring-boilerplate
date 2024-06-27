package ghojeong.log.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceLog {
    private static final int MESSAGE_LENGTH = 16777215;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_seq")
    private Long logSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_level", updatable = false)
    private LogLevel logLevel;

    @Column(name = "message", updatable = false)
    private String message;

    public static DeviceLog of(
            LogLevel logLevel,
            String message
    ) {
        return DeviceLog.builder()
                .logLevel(logLevel)
                .message(message)
                .build()
                .compress();
    }

    private DeviceLog compress() {
        final int BEGIN_INDEX = 0;
        if (message != null && message.length() > MESSAGE_LENGTH) {
            message = message.substring(BEGIN_INDEX, MESSAGE_LENGTH);
        }
        return this;
    }
}
