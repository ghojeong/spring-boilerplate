package ghojeong.common.dto.response;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class ExceptionResponse<T extends Exception> extends CommonResponse<String> {
    private static final String LOG_MESSAGE = "timestamp(%s), code(%d), message(%s)";

    @SuppressWarnings("this-escape")
    public ExceptionResponse(HttpStatus httpStatus, T exception) {
        super(httpStatus, exception.getMessage(), exception.getClass().getName());
        log.error(toString());
    }

    @Override
    public String toString() {
        return String.format(LOG_MESSAGE, getTimestamp(), getCode(), getMessage());
    }
}
