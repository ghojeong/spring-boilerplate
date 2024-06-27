package ghojeong.user.domain;

import ghojeong.user.domain.entity.User;
import ghojeong.user.domain.type.UserType;

public record UserIdentifier(
        UserType userType,
        String ssoId
) {
    private static final String IDENTIFIER_DELIMITER = "__";

    public static UserIdentifier of(User user) {
        return new UserIdentifier(
                user.getType(),
                user.getSsoId()
        );
    }

    public static UserIdentifier parseAuthName(String authName) {
        String[] splitted = authName.split(IDENTIFIER_DELIMITER);
        UserType userType = UserType.of(splitted[0]);
        String ssoId = splitted[1];
        return new UserIdentifier(userType, ssoId);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", userType.name(), IDENTIFIER_DELIMITER, ssoId);
    }
}
