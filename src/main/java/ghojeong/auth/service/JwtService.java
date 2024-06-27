package ghojeong.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ghojeong.auth.domain.AuthType;
import ghojeong.auth.domain.JwtDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String TYPE_CLAIM = "type";
    @Value("${security.jwt.token.issuer}")
    private String issuer;
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;


    public String createToken(JwtDetails details) {
        return JWT.create()
                .withJWTId(details.id())
                .withSubject(details.authName())
                .withIssuer(issuer)
                .withIssuedAt(details.issuedAt())
                .withExpiresAt(details.expiration())
                .withClaim(TYPE_CLAIM, details.type().name())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public JwtDetails decodeToken(String token) {
        token = token.replace("Bearer ", "");
        DecodedJWT decoded = JWT
                .require(Algorithm.HMAC512(secretKey))
                .withIssuer(issuer)
                .build()
                .verify(token);
        return JwtDetails.builder()
                .id(decoded.getId())
                .authName(decoded.getSubject())
                .type(AuthType.of(decoded.getClaim(TYPE_CLAIM).asString()))
                .issuedAt(decoded.getIssuedAt())
                .expiration(decoded.getExpiresAt())
                .build();
    }

    public boolean isInvalidToken(String token) {
        if (token == null) {
            return true;
        }
        try {
            return decodeToken(token)
                    .expiration()
                    .before(new Date());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            return true;
        }
    }
}
