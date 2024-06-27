package ghojeong.auth.controller;

import ghojeong.auth.domain.MemberUserDetails;
import ghojeong.auth.domain.UserDetailsAuthToken;
import ghojeong.auth.dto.request.LogInRequest;
import ghojeong.auth.dto.request.RefreshTokenRequest;
import ghojeong.auth.dto.request.SignUpRequest;
import ghojeong.auth.dto.response.MemberLoginResponse;
import ghojeong.auth.dto.response.RefreshTokenResponse;
import ghojeong.auth.service.MemberAuthService;
import ghojeong.user.domain.entity.User;
import ghojeong.user.service.UserCommandService;
import ghojeong.user.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {
    private final MemberAuthService memberAuthService;
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return memberAuthService.refreshToken(request);
    }

    @PostMapping("/signup")
    public MemberLoginResponse signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        User user = userCommandService.signUp(request);
        return memberAuthService.login(
                new UserDetailsAuthToken(new MemberUserDetails(user))
        );
    }

    @PostMapping("/login")
    public MemberLoginResponse logIn(
            @Valid @RequestBody LogInRequest request
    ) {
        User user = userQueryService.findUser(request);
        return memberAuthService.login(
                new UserDetailsAuthToken(new MemberUserDetails(user))
        );
    }
}
