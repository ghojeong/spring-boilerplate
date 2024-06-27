package ghojeong.user.domain.pk;

import ghojeong.user.domain.type.UserType;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserBannedPK implements Serializable {
    private UserType type;
    private String ssoId;
}
