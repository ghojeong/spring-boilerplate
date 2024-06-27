package ghojeong.admin.acceptance;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.domain.type.AdminType;
import ghojeong.admin.dto.request.AdminLoginRequest;
import ghojeong.admin.dto.request.CreateAdminRequest;
import ghojeong.admin.dto.request.DeleteAdminRequest;
import ghojeong.admin.dto.request.UpdateAdminNicknameRequest;
import ghojeong.admin.dto.request.UpdateAdminPasswordRequest;
import ghojeong.admin.dto.request.UpdateAdminPermissionRequest;
import ghojeong.common.CommonFixture;

import java.util.List;

final class AdminFixture extends CommonFixture {
    private AdminFixture() {}

    private static AdminUser createAdminUser() {
        return AdminUser.builder()
                .email("admin@ghojeong.com")
                .password("ADMIN_PASSWORD")
                .nickname("Administrator")
                .type(AdminType.ADMIN)
                .build();
    }

    static CreateAdminRequest createAdminCreateRequest() {
        AdminUser admin = createAdminUser();
        return CreateAdminRequest.builder()
                .email(admin.getEmail())
                .password(admin.getPassword())
                .nickname(admin.getNickname())
                .build();
    }

    static DeleteAdminRequest createAdminDeleteRequest() {
        AdminUser admin = createAdminUser();
        return DeleteAdminRequest.builder()
                .email(admin.getEmail())
                .build();
    }

    static AdminLoginRequest createAdminLoginRequest() {
        AdminUser admin = createAdminUser();
        return AdminLoginRequest.builder()
                .email(admin.getEmail())
                .password(admin.getPassword())
                .build();
    }

    static UpdateAdminNicknameRequest createAdminUpdateNicknameRequest(String nickname) {
        return UpdateAdminNicknameRequest.builder()
                .nickname(nickname)
                .build();
    }

    static UpdateAdminPasswordRequest createAdminUpdatePasswordRequest(String password) {
        return UpdateAdminPasswordRequest.builder()
                .password(password)
                .build();
    }

    static UpdateAdminPermissionRequest createAdminUpdatePermissionRequest(List<String> permission) {
        AdminUser admin = createAdminUser();
        return UpdateAdminPermissionRequest.builder()
                .email(admin.getEmail())
                .permission(permission)
                .build();
    }
}
