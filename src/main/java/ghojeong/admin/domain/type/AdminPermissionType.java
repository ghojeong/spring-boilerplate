package ghojeong.admin.domain.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// NOTE: Spring Security 의 Authority 호환성을 위해 ROLE_ prefix 를 의도적으로 붙였다.
// https://www.baeldung.com/spring-security-expressions#2-hasauthority-hasanyauthority
public enum AdminPermissionType {
    ROLE_SUPER,
    ROLE_DASHBOARD,
    ROLE_DISCOVER,
    ROLE_QUOTE,
    ROLE_USER,
    ROLE_PERSONA_USER,
    ROLE_ORDER,
    ROLE_SEGMENT,
    ROLE_GOAL,
    ROLE_REPORT,
    ROLE_ADMIN,
    ROLE_MENU,
    ROLE_FEED,
    ROLE_PROFILE_TAG,
    ROLE_NOTICE;

    private static final String DELIMITER = ",";

    public static AdminPermissionType of(String type) {
        return valueOf(type.toUpperCase());
    }

    public static List<AdminPermissionType> toList(String permission) {
        if (permission == null || permission.isBlank()) {
            return List.of(ROLE_ADMIN);
        }
        return Arrays.stream(permission.split(DELIMITER))
                .map(AdminPermissionType::of)
                .toList();
    }

    public static String toString(List<AdminPermissionType> permissions) {
        return String.join(
                DELIMITER,
                permissions.stream().map(
                        AdminPermissionType::name
                ).collect(Collectors.toSet())
        );
    }

    @Override
    public String toString() {
        return name();
    }
}
