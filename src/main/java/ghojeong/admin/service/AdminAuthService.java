package ghojeong.admin.service;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.dto.request.AdminLoginRequest;
import ghojeong.admin.dto.response.AdminLoginResponse;
import ghojeong.admin.dto.response.data.AdminLoginDto;
import ghojeong.admin.exception.AdminLoginFailedException;
import ghojeong.admin.exception.AdminNotFoundException;
import ghojeong.admin.repository.AdminRepository;
import ghojeong.auth.domain.AuthType;
import ghojeong.auth.domain.JwtDetails;
import ghojeong.auth.service.JwtService;
import ghojeong.auth.util.SHA256Util;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static ghojeong.common.util.DateTimeParser.toLocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminAuthService {
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    @Value("${security.jwt.token.access-expire-length}")
    private Long accessExpireLength;

    public AdminLoginResponse login(AdminLoginRequest request) {
        final AdminUser user = authenticate(request);
        Date issuedAt = new Date();
        Date accessTokenExpiration = new Date(issuedAt.getTime() + accessExpireLength);

        JwtDetails accessTokenDetails = JwtDetails.builder()
                .authName(user.getEmail())
                .type(AuthType.ADMIN)
                .issuedAt(issuedAt)
                .expiration(accessTokenExpiration)
                .build();

        AdminLoginDto body = AdminLoginDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .accessToken(jwtService.createToken(accessTokenDetails))
                .accessTokenExpiredAt(toLocalDateTime(accessTokenExpiration))
                .build();
        return new AdminLoginResponse(body);
    }

    private AdminUser authenticate(AdminLoginRequest request) {
        final AdminUser user = findByEmail(request.email());
        final boolean isValid = SHA256Util.comparePassword(
                request.password(),
                user.getPassword()
        );
        if (!isValid) {
            throw new AdminLoginFailedException();
        }
        return user;
    }

    private AdminUser findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException(email));
    }
}
