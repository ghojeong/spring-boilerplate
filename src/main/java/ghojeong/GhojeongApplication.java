package ghojeong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static ghojeong.common.util.DateTimeParser.LOCAL_TIME_ZONE;

@SpringBootApplication
public class GhojeongApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(LOCAL_TIME_ZONE));
        SpringApplication.run(GhojeongApplication.class, args);
    }
}
