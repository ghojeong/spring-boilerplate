package ghojeong.user.controller;

import ghojeong.user.dto.request.CreateNicknamesRequest;
import ghojeong.user.service.UserNicknameService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNicknameController {
    private final UserNicknameService userNicknameService;

    /*
curl -X POST http://localhost:8080/v1/users/nicknames \
-H "Content-Type: application/json"  \
-d @nicknames.json
     */

    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PostMapping("/users/nicknames")
    public void createUserNicknames(
            @Valid @RequestBody CreateNicknamesRequest request
    ) {
        userNicknameService.saveAll(request);
    }
}
