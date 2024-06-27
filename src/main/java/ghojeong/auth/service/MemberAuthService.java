package ghojeong.auth.service;

import ghojeong.auth.domain.AuthType;
import ghojeong.auth.domain.JwtDetails;
import ghojeong.auth.dto.request.RefreshTokenRequest;
import ghojeong.auth.dto.response.MemberLoginResponse;
import ghojeong.auth.dto.response.RefreshTokenResponse;
import ghojeong.auth.dto.response.data.MemberLoginDto;
import ghojeong.auth.dto.response.data.RefreshTokenDto;
import ghojeong.auth.exception.AuthMemberNotFoundException;
import ghojeong.auth.exception.InvalidTokenException;
import ghojeong.user.domain.UserIdentifier;
import ghojeong.user.domain.entity.UserWithTags;
import ghojeong.user.service.UserQueryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static ghojeong.common.util.DateTimeParser.toLocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthService {
    private final UserQueryService userQueryService;
    private final JwtService jwtService;

    @Value("${security.jwt.token.access-expire-length}")
    private Long accessExpireLength;

    @Value("${security.jwt.token.refresh-expire-length}")
    private Long refreshExpireLength;

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (jwtService.isInvalidToken(refreshToken)) {
            throw new InvalidTokenException(refreshToken);
        }
        JwtDetails details = jwtService.decodeToken(refreshToken);
        UserWithTags user = findUserByIdentifier(
                UserIdentifier.parseAuthName(details.authName())
        );
        Date issuedAt = new Date();
        Date accessTokenExpiration = new Date(issuedAt.getTime() + accessExpireLength);
        String accessToken = jwtService.createToken(
                details.clone(issuedAt, accessTokenExpiration)
        );
        RefreshTokenDto body = RefreshTokenDto.builder()
                .seq(user.getUserSeq())
                .userSeq(user.getUserSeq())
                .type(user.getTypeName())
                .ssoId(user.getSsoId())
                .accessToken(accessToken)
                .accessTokenExpiredAt(toLocalDateTime(accessTokenExpiration))
                .build();
        return new RefreshTokenResponse(body);
    }

    public MemberLoginResponse login(Authentication authentication) {
        String authName = authentication.getName();
        UserWithTags user = findUserByIdentifier(
                UserIdentifier.parseAuthName(authName)
        );
        Date issuedAt = new Date();
        Date accessTokenExpiration = new Date(issuedAt.getTime() + accessExpireLength);
        Date refreshTokenExpiration = new Date(issuedAt.getTime() + refreshExpireLength);

        JwtDetails accessTokenDetails = JwtDetails.builder()
                .authName(authName)
                .type(AuthType.MEMBER)
                .issuedAt(issuedAt)
                .expiration(accessTokenExpiration)
                .build();

        JwtDetails refreshTokenDetails = JwtDetails.builder()
                .authName(authName)
                .type(AuthType.MEMBER)
                .issuedAt(issuedAt)
                .expiration(refreshTokenExpiration)
                .build();

        MemberLoginDto body = MemberLoginDto.builder()
                .seq(user.getUserSeq())
                .userSeq(user.getUserSeq())
                .type(user.getType().name())
                .ssoId(user.getSsoId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .biography(user.getBiography())
                .tags(user.getTags())
                .morningReminderTime(user.getMorningReminderTime())
                .eveningReminderTime(user.getEveningReminderTime())
                .isMorningReminderActive(user.getIsMorningReminderActive())
                .isEveningReminderActive(user.getIsEveningReminderActive())
                .isPrivateAccount(user.getIsPrivateAccount())
                .offlineUpdatedAt(user.getOfflineUpdatedAt())
                .accessToken(jwtService.createToken(accessTokenDetails))
                .refreshToken(jwtService.createToken(refreshTokenDetails))
                .accessTokenExpiredAt(toLocalDateTime(accessTokenExpiration))
                .refreshTokenExpiredAt(toLocalDateTime(refreshTokenExpiration))
                .build();
        return new MemberLoginResponse(body);
    }

    private UserWithTags findUserByIdentifier(UserIdentifier identifier) {
        return userQueryService.findUserWithTags(
                identifier.userType(), identifier.ssoId()
        ).stream().findFirst().orElseThrow(
                () -> new AuthMemberNotFoundException(identifier.toString())
        );
    }
}
