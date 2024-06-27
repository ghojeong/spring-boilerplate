package ghojeong.log.repository;

import ghojeong.auth.domain.AuthAuditor;
import ghojeong.log.config.LogJdbcTemplate;
import ghojeong.log.domain.ApiLog;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class JdbcApiLogRepository
        extends JdbcLogRepository<ApiLog>
        implements ApiLogRepository {

    protected JdbcApiLogRepository(
            AuthAuditor authAuditor,
            LogJdbcTemplate jdbcTemplate
    ) {
        super(authAuditor, jdbcTemplate);
    }

    @Override
    String getTableName() {
        return "api_log";
    }

    @Override
    Map<String, Object> toMap(ApiLog entity) {
        return new LinkedHashMap<>() {{
            put("duration_millisecond", entity.getDuration());
            put("status_code", entity.getStatusCode());
            put("http_method", entity.getHttpMethod());
            put("http_uri", entity.getHttpUri());
            put("parameter", entity.getParameter());
            put("user_agent", entity.getUserAgent());
            put("auth_token", entity.getAuthToken());
            put("request_header", entity.getRequestHeader());
            put("response_header", entity.getResponseHeader());
            put("request_body", entity.getRequestBody());
            put("response_body", entity.getResponseBody());
            put("creator", getCreator());
        }};
    }
}
