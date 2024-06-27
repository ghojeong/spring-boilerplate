package ghojeong.user.domain.type;

public enum UserRoleType {
    ROLE_MEMBER,
    ROLE_PERSONA;

    public static UserRoleType of(String type) {
        return valueOf(type.toUpperCase());
    }

    @Override
    public String toString() {
        return name();
    }
}
