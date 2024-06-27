package ghojeong.auth.handler;

import ghojeong.auth.util.JsonMapper;
import ghojeong.common.dto.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordAuthFailureHandler implements AuthenticationFailureHandler {
    private final JsonMapper jsonMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        response.setStatus(status.value());
        jsonMapper.writeAsJson(
                response,
                new ExceptionResponse<>(status, exception)
        );
    }
}
