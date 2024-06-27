package ghojeong.admin.controller;

import ghojeong.admin.dto.request.AdminLoginRequest;
import ghojeong.admin.dto.response.AdminLoginResponse;
import ghojeong.admin.service.AdminAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    @PostMapping("/admin/login")
    public AdminLoginResponse login(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        return adminAuthService.login(request);
    }
}
