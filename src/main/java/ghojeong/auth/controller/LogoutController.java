package ghojeong.auth.controller;

import ghojeong.auth.domain.MemberUserDetails;
import ghojeong.auth.dto.response.LogoutResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutController {
    @Secured("ROLE_MEMBER")
    @PostMapping("/logout")
    public LogoutResponse postDevice(
            @AuthenticationPrincipal MemberUserDetails userDetails
    ) {
        final Long userSeq = userDetails.getUserSeq();
        return new LogoutResponse(userSeq);
    }
}
