package ghojeong.common.util;

import ghojeong.common.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeParser {
    public static final String LOCAL_TIME_ZONE = "Asia/Seoul";
    public static final String TIME_ZONE_HEADER = "Time-Zone";
    public static final String ZONED_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm";
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.of(LOCAL_TIME_ZONE);
    private static final DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String format(Date date) {
        return date == null ? null
                : dateFormat.format(date);
    }

    public static String format(LocalDateTime localDateTime) {
        return localDateTime == null ? null
                : localDateTime.format(dateTimeFormatter);
    }

    public static String format(LocalDate localDate) {
        return localDate == null ? null
                : localDate.format(dateFormatter);
    }

    public static String format(LocalTime localTime) {
        return localTime == null ? null
                : localTime.format(timeFormatter);
    }

    public static ZonedDateTime toZonedDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null
                : dateTime.atZone(LOCAL_ZONE_ID);
    }

    public static LocalDate toZonedLocalDate(String timeZone) {
        try {
            return ZonedDateTime.now(ZoneId.of(timeZone))
                    .toLocalDate();
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public static LocalDateTime toStartOfDay(String timeZone) {
        try {
            final ZoneId zoneId = ZoneId.of(timeZone);
            return ZonedDateTime.now(zoneId).toLocalDate()
                    .atStartOfDay().atZone(zoneId)
                    .withZoneSameInstant(LOCAL_ZONE_ID)
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public static LocalDateTime toZonedLocalDateTime(String timeZone) {
        try {
            return ZonedDateTime.now(ZoneId.of(timeZone))
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return dateTime == null ? null
                : LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null
                : LocalDateTime.parse(format(date), dateTimeFormatter);
    }

    public static LocalDateTime toLocalDateTime(LocalDate date) {
        return date == null ? null
                : date.atStartOfDay();
    }

    public static LocalDate toLocalDate(String date) {
        return date == null ? null
                : LocalDate.parse(date, dateFormatter);
    }

    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime == null ? null
                : dateTime.toLocalDate();
    }

    public static LocalTime toLocalTime(String time) {
        return time == null ? null
                : LocalTime.parse(time, timeFormatter);
    }

    public static LocalDateTime toDeletedAt(Boolean isDeleted) {
        return isDeleted == null || isDeleted ? null
                : LocalDateTime.now();
    }
}
