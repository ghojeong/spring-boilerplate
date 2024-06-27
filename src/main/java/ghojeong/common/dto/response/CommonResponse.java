package ghojeong.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;

@Getter
public class CommonResponse<T> {
    private final String apiVersion = "1.1.0";
    @JsonFormat(pattern = DATETIME_PATTERN)
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String endpoint;
    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
    private final T data;

    public CommonResponse(HttpStatus httpStatus, String message, T data) {
        this.endpoint = "";
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public CommonResponse<T> setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }
}
