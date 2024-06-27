package ghojeong.user.domain.pk;

import ghojeong.user.domain.type.UserRoleType;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRolePK implements Serializable {
    private UserRoleType role;
    private Long userSeq;
}
