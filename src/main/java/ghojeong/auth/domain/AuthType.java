package ghojeong.auth.domain;

public enum AuthType {
    MEMBER,
    ADMIN;

    public static AuthType of(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (Exception e) {
            return MEMBER;
        }
    }

    @Override
    public String toString() {
        return name();
    }
}
