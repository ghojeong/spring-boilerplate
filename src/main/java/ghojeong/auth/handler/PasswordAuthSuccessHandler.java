package ghojeong.auth.handler;

import ghojeong.auth.dto.response.MemberLoginResponse;
import ghojeong.auth.service.MemberAuthService;
import ghojeong.auth.util.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JsonMapper jsonMapper;
    private final MemberAuthService memberAuthService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        response.setStatus(MemberLoginResponse.HTTP_STATUS.value());
        jsonMapper.writeAsJson(
                response,
                memberAuthService.login(authentication)
        );
    }
}
