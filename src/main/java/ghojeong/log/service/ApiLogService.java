package ghojeong.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ghojeong.log.domain.ApiLog;
import ghojeong.log.repository.ApiLogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiLogService {
    private static final String EMPTY_BODY = null;
    private static final String EMPTY_HEADER = null;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEADER_FORMAT = "%s: %s ";
    private final ObjectMapper objectMapper;
    private final ApiLogRepository apiLogRepository;
    private final Environment env;

    public void saveApiLog(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration
    ) {
        ApiLog apiLog = createApiLog(request, response, duration);
        if (apiLog.checkSavable()) {
            apiLogRepository.save(apiLog);
        }
    }

    private ApiLog createApiLog(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration
    ) {
        return ApiLog.builder()
                .duration(duration)
                .statusCode(response.getStatus())
                .httpMethod(request.getMethod())
                .httpUri(request.getRequestURI())
                .parameter(parseParameter(request))
                .userAgent(request.getHeader("user-agent"))
                .authToken(request.getHeader("authorization"))
                .requestHeader(parseHeader(request))
                .responseHeader(parseHeader(response))
                .requestBody(parseBody(request))
                .responseBody(parseBody(response))
                .build().compress(getBodyLength());
    }

    private int getBodyLength() {
        final boolean isProd = Arrays.asList(env.getActiveProfiles())
                .contains("prod");
        return isProd ? 500 : 16700000;
    }

    private boolean isValidContentType(String contentType) {
        return contentType == null
                || contentType.isBlank()
                || !contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    private String parseBody(ContentCachingRequestWrapper request) {
        if (isValidContentType(request.getContentType())) {
            return EMPTY_BODY;
        }
        return parseBody(request.getContentAsByteArray());
    }

    private String parseBody(ContentCachingResponseWrapper response) {
        if (isValidContentType(response.getContentType())) {
            return EMPTY_BODY;
        }
        return parseBody(response.getContentAsByteArray());
    }

    private boolean isValidBody(byte[] body) {
        return body != null && body.length > 0;
    }

    private String parseBody(byte[] body) {
        try {
            return isValidBody(body) ?
                    objectMapper.readTree(body).toPrettyString()
                    : EMPTY_BODY;
        } catch (IOException e) {
            return EMPTY_BODY;
        }
    }

    private String parseParameter(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.isEmpty() ? null
                : request.getParameterMap().entrySet().stream()
                .map(entry -> String.format(
                        "%s=%s ",
                        entry.getKey(),
                        String.join(",", entry.getValue())
                )).collect(Collectors.joining(LINE_SEPARATOR));
    }

    private String parseHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return EMPTY_HEADER;
        }
        StringBuilder strBuilder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            strBuilder.append(String.format(
                    HEADER_FORMAT,
                    headerName,
                    request.getHeader(headerName)
            ));
            strBuilder.append(LINE_SEPARATOR);
        }
        return strBuilder.toString();
    }

    private String parseHeader(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        return headerNames == null ? EMPTY_HEADER
                : headerNames.stream().map(
                headerName -> String.format(
                        HEADER_FORMAT,
                        headerName,
                        response.getHeader(headerName)
                )).collect(Collectors.joining(LINE_SEPARATOR));
    }
}
