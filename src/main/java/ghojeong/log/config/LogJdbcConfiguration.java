package ghojeong.log.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


// NOTE: https://www.baeldung.com/spring-boot-configure-multiple-datasources
@Configuration
public class LogJdbcConfiguration {
    @Bean
    protected LogJdbcTemplate logJdbcTemplate(LogProperties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.url);
        hikariConfig.setDriverClassName(properties.driverClassName);
        hikariConfig.setUsername(properties.username);
        hikariConfig.setPassword(properties.password);
        hikariConfig.setMaximumPoolSize(properties.maximumPoolSize);
        return new LogJdbcTemplate(new HikariDataSource(hikariConfig));
    }

    @Data
    @Component
    @ConfigurationProperties(prefix = "spring.datasource-log")
    protected static class LogProperties {
        private String url;
        private String driverClassName;
        private String username;
        private String password;
        private Integer maximumPoolSize;
    }
}
