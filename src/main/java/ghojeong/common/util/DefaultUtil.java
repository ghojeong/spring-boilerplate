package ghojeong.common.util;

import java.time.LocalDateTime;

public final class DefaultUtil {
    private static final int POSITIVE_LOWER_BOUND = 1;

    private DefaultUtil() {}

    public static Integer getPositive(Integer number) {
        return number != null && number >= POSITIVE_LOWER_BOUND
                ? number : POSITIVE_LOWER_BOUND;
    }

    public static LocalDateTime getDeletedAt(Boolean isDeleted) {
        return isDeleted != null && isDeleted
                ? LocalDateTime.now() : null;
    }

    public static boolean getIsDeleted(LocalDateTime deletedAt) {
        return deletedAt != null;
    }
}
