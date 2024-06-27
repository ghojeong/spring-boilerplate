package ghojeong.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ghojeong.common.exception.BadRequestException;

public final class JacksonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JacksonMapper() {}

    public static String toString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("JacksonMapper Failed");
        }
    }
}
