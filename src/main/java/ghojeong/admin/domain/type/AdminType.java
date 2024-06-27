package ghojeong.admin.domain.type;

public enum AdminType {
    SUPER_ADMIN,
    ADMIN;

    public static AdminType of(String type) {
        return valueOf(type.toUpperCase());
    }

    @Override
    public String toString() {
        return name();
    }
}
