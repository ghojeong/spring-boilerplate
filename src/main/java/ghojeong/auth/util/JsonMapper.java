package ghojeong.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JsonMapper {
    private static final String CHAR_ENCODING = "utf-8";
    private final ObjectMapper objectMapper;

    public <T> T readRequest(
            HttpServletRequest request,
            Class<T> valueType
    ) throws IOException {
        return objectMapper.readValue(
                StreamUtils.copyToString(
                        request.getInputStream(),
                        StandardCharsets.UTF_8
                ),
                valueType
        );
    }

    public void writeAsJson(
            HttpServletResponse response,
            Object value
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHAR_ENCODING);
        response.getWriter().write(
                objectMapper.writeValueAsString(value)
        );
    }
}
