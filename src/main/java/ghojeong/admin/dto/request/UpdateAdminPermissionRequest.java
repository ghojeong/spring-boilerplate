package ghojeong.admin.dto.request;

import ghojeong.admin.domain.type.AdminPermissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateAdminPermissionRequest(
        @NotBlank
        String email,
        @NotNull
        List<String> permission

) {
    public List<AdminPermissionType> getPermission() {
        return permission.stream()
                .map(AdminPermissionType::of)
                .toList();
    }
}
