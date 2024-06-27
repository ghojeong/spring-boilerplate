package ghojeong.user.domain.type;

import ghojeong.common.util.EnumUtil;

public enum UserType {
    EMAIL,
    GOOGLE,
    FACEBOOK,
    APPLE;

    public static UserType of(String type) {
        return valueOf(type.toUpperCase());
    }

    public static String toEnumString() {
        return EnumUtil.toEnumString(values());
    }

    @Override
    public String toString() {
        return name();
    }
}
