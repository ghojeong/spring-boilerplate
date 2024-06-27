package ghojeong.common.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class EnumUtil {
    private static final String DELIMITER = " | ";

    public static String toEnumString(Enum<?>[] values) {
        return Arrays.stream(values)
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }
}
