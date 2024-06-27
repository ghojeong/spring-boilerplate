package ghojeong.log.repository;

import ghojeong.auth.domain.AuthAuditor;
import ghojeong.log.config.LogJdbcTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class JdbcLogRepository<T> {
    private static final String DELIMITER = ", ";
    private final AuthAuditor authAuditor;
    private final LogJdbcTemplate jdbcTemplate;

    abstract String getTableName();

    abstract Map<String, Object> toMap(T entity);

    protected String getCreator() {
        return authAuditor.getCurrentAuditor()
                .orElse(null);
    }

    public T save(T entity) {
        Map<String, Object> mapper = toMap(entity);
        jdbcTemplate.execute(
                buildInsertCommand(mapper),
                mapper,
                PreparedStatement::executeUpdate
        );
        return entity;
    }

    private String buildInsertCommand(Map<String, Object> mapper) {
        List<String> keys = new ArrayList<>(mapper.keySet());
        List<String> parameters = keys.stream()
                .map(key -> String.format(":%s", key))
                .toList();
        return " INSERT INTO " + getTableName() +
                " ( " + String.join(DELIMITER, keys) +
                " ) VALUES ( " +
                String.join(DELIMITER, parameters) +
                " ) ";
    }
}
