package ghojeong.admin.controller;

import ghojeong.admin.dto.request.CreateAdminRequest;
import ghojeong.admin.dto.request.DeleteAdminRequest;
import ghojeong.admin.dto.request.UpdateAdminNicknameRequest;
import ghojeong.admin.dto.request.UpdateAdminPasswordRequest;
import ghojeong.admin.dto.request.UpdateAdminPermissionRequest;
import ghojeong.admin.dto.response.CreateAdminResponse;
import ghojeong.admin.dto.response.DeleteAdminResponse;
import ghojeong.admin.dto.response.UpdateAdminResponse;
import ghojeong.admin.service.AdminService;
import ghojeong.auth.domain.AdminUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminController {
    private final AdminService adminService;

    @Secured("ROLE_SUPER")
    @PostMapping("/admin")
    public CreateAdminResponse create(
            @Valid @RequestBody CreateAdminRequest request
    ) {
        return adminService.create(request);
    }

    @PutMapping("/admin/me/nickname")
    public UpdateAdminResponse updateNickname(
            @AuthenticationPrincipal AdminUserDetails userDetails,
            @Valid @RequestBody UpdateAdminNicknameRequest request
    ) {
        return adminService.updateNickname(userDetails.getAdminEmail(), request);
    }

    @PutMapping("/admin/me/password")
    public UpdateAdminResponse updatePassword(
            @AuthenticationPrincipal AdminUserDetails userDetails,
            @Valid @RequestBody UpdateAdminPasswordRequest request
    ) {
        return adminService.updatePassword(userDetails.getAdminEmail(), request);
    }

    @Secured("ROLE_SUPER")
    @PutMapping("/admin/permission")
    public UpdateAdminResponse updatePermission(
            @Valid @RequestBody UpdateAdminPermissionRequest request
    ) {
        return adminService.updatePermission(request);
    }

    @Secured("ROLE_SUPER")
    @DeleteMapping("/admin")
    public DeleteAdminResponse delete(
            @Valid @RequestBody DeleteAdminRequest request
    ) {
        return adminService.delete(request);
    }
}
