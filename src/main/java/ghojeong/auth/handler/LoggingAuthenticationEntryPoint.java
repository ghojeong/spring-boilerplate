package ghojeong.auth.handler;

import ghojeong.auth.util.JsonMapper;
import ghojeong.common.dto.response.ExceptionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoggingAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HttpStatus HTTP_STATUS = FORBIDDEN;
    private final JsonMapper jsonMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        response.setStatus(HTTP_STATUS.value());
        jsonMapper.writeAsJson(
                response,
                new ExceptionResponse<>(HTTP_STATUS, exception)
        );
    }
}
