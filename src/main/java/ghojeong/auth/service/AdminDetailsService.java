package ghojeong.auth.service;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.repository.AdminRepository;
import ghojeong.auth.domain.AdminUserDetails;
import ghojeong.auth.exception.AuthAdminNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new AdminUserDetails(adminRepository.save(
                findByEmail(username).refreshLastActivity()
        ));
    }

    private AdminUser findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new AuthAdminNotFoundException(email));
    }
}
