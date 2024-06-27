package ghojeong.admin.service;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.dto.request.CreateAdminRequest;
import ghojeong.admin.dto.request.DeleteAdminRequest;
import ghojeong.admin.dto.request.UpdateAdminNicknameRequest;
import ghojeong.admin.dto.request.UpdateAdminPasswordRequest;
import ghojeong.admin.dto.request.UpdateAdminPermissionRequest;
import ghojeong.admin.dto.response.CreateAdminResponse;
import ghojeong.admin.dto.response.DeleteAdminResponse;
import ghojeong.admin.dto.response.UpdateAdminResponse;
import ghojeong.admin.exception.AdminNotFoundException;
import ghojeong.admin.repository.AdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminService {
    private final AdminRepository adminRepository;

    public CreateAdminResponse create(CreateAdminRequest request) {
        return CreateAdminResponse.of(
                adminRepository.save(request.toEntity())
        );
    }

    public UpdateAdminResponse updateNickname(String adminEmail, UpdateAdminNicknameRequest request) {
        AdminUser adminUser = findByEmail(adminEmail)
                .setNickname(request.nickname());
        return UpdateAdminResponse.of(adminRepository.save(adminUser));
    }

    public UpdateAdminResponse updatePassword(String adminEmail, UpdateAdminPasswordRequest request) {
        AdminUser adminUser = findByEmail(adminEmail)
                .setPassword(request.password());
        return UpdateAdminResponse.of(adminRepository.save(adminUser));
    }

    public UpdateAdminResponse updatePermission(UpdateAdminPermissionRequest request) {
        AdminUser adminUser = findByEmail(request.email())
                .setPermission(request.getPermission());
        return UpdateAdminResponse.of(adminRepository.save(adminUser));
    }

    public DeleteAdminResponse delete(DeleteAdminRequest request) {
        final String email = request.email();
        adminRepository.save(
                findByEmail(email).delete()
        );
        return DeleteAdminResponse.of(email);
    }

    private AdminUser findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException(email));
    }
}
