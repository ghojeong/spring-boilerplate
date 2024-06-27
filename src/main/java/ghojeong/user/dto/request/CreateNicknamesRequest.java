package ghojeong.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ghojeong.user.domain.entity.UserNickname;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;

public record CreateNicknamesRequest(
        @NotNull
        @JsonProperty("nicknames")
        List<String> nicknames
) {
    public List<UserNickname> toEntity() {
        return new HashSet<>(nicknames).stream()
                .distinct()
                .map(UserNickname::new)
                .toList();
    }
}
