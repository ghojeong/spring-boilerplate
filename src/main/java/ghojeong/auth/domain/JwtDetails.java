package ghojeong.auth.domain;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record JwtDetails(
        String id,
        String authName,
        AuthType type,
        Date issuedAt,
        Date expiration
) {
    @Override
    public String id() {
        return id == null
                ? createId()
                : id;
    }

    public JwtDetails clone(Date issuedAt, Date expiration) {
        return JwtDetails.builder()
                .id(createId())
                .authName(authName)
                .type(type)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .build();
    }

    private String createId() {
        return UUID.randomUUID().toString();
    }
}
