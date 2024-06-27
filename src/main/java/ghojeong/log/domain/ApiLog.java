package ghojeong.log.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "api_log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiLog {
    private static final int USER_AGENT_LENGTH = 400;
    private static final int AUTH_TOKEN_LENGTH = 400;
    private static final int REQUEST_HEADER_LENGTH = 1000;
    private static final int RESPONSE_HEADER_LENGTH = 400;
    private static final int BODY_LENGTH = 16777215;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_log_seq")
    private Long apiLogSeq;

    @Column(name = "duration_millisecond")
    private Long duration;
    @Column(name = "status_code")
    private Integer statusCode;
    @Column(name = "http_method")
    private String httpMethod;
    @Column(name = "http_uri")
    private String httpUri;
    @Column(name = "parameter")
    private String parameter;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "auth_token")
    private String authToken;
    @Column(name = "request_header")
    private String requestHeader;
    @Column(name = "response_header")
    private String responseHeader;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "response_body")
    private String responseBody;

    public ApiLog compress(int bodyLength) {
        final int BEGIN_INDEX = 0;
        if (userAgent != null && userAgent.length() > USER_AGENT_LENGTH) {
            userAgent = userAgent.substring(BEGIN_INDEX, USER_AGENT_LENGTH);
        }
        if (authToken != null && authToken.length() > AUTH_TOKEN_LENGTH) {
            authToken = authToken.substring(BEGIN_INDEX, AUTH_TOKEN_LENGTH);
        }
        if (requestHeader != null && requestHeader.length() > REQUEST_HEADER_LENGTH) {
            requestHeader = requestHeader.substring(BEGIN_INDEX, REQUEST_HEADER_LENGTH);
        }
        if (responseHeader != null && responseHeader.length() > RESPONSE_HEADER_LENGTH) {
            responseHeader = responseHeader.substring(BEGIN_INDEX, RESPONSE_HEADER_LENGTH);
        }
        if (requestBody != null && requestBody.length() > bodyLength) {
            requestBody = requestBody.substring(BEGIN_INDEX, bodyLength);
        }
        if (responseBody != null && responseBody.length() > bodyLength) {
            responseBody = responseBody.substring(BEGIN_INDEX, bodyLength);
        }
        return this;
    }

    public boolean checkSavable() {
        return statusCode >= 200 && statusCode < 600
                && !httpUri.startsWith("/doc")
                && (userAgent == null || userAgent.length() <= USER_AGENT_LENGTH)
                && (authToken == null || authToken.length() <= AUTH_TOKEN_LENGTH)
                && (requestHeader == null || requestHeader.length() <= REQUEST_HEADER_LENGTH)
                && (responseHeader == null || responseHeader.length() <= RESPONSE_HEADER_LENGTH)
                && (requestBody == null || requestBody.length() <= BODY_LENGTH)
                && (responseBody == null || responseBody.length() <= BODY_LENGTH);
    }
}
