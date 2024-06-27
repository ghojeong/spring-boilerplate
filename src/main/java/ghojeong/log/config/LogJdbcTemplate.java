package ghojeong.log.config;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class LogJdbcTemplate extends NamedParameterJdbcTemplate {
    public LogJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }
}
