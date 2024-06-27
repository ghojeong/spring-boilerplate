package ghojeong.auth.domain;

import ghojeong.admin.domain.entity.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminUserDetails implements UserDetails {

    private final AdminUser adminUser;

    public AdminUserDetails(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public Long getAdminUserSeq() {
        return adminUser.getAdminUserSeq();
    }

    public String getAdminEmail() {
        return adminUser.getEmail();
    }

    public String getNickname() {
        return adminUser.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return adminUser.getPermissionName().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return adminUser.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return adminUser.isAvailable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return adminUser.isAvailable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return adminUser.isAvailable();
    }
}
