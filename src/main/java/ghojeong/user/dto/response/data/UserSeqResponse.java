package ghojeong.user.dto.response.data;

import ghojeong.user.domain.entity.User;
import lombok.Builder;

@Builder
public record UserSeqResponse(
        Long seq,
        Long userSeq
) {
    public static UserSeqResponse of(User user) {
        return UserSeqResponse.builder()
                .seq(user.getUserSeq())
                .userSeq(user.getUserSeq())
                .build();
    }
}
