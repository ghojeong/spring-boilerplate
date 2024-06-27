package ghojeong.user.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserTagPK implements Serializable {
    private String userTag;
    private Long userSeq;
}
