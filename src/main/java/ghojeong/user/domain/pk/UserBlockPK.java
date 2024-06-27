package ghojeong.user.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBlockPK implements Serializable {
    private Long blockingUserSeq;
    private Long blockedUserSeq;
}
