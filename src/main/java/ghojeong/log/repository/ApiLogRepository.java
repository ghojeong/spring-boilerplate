package ghojeong.log.repository;

import ghojeong.log.domain.ApiLog;

public interface ApiLogRepository {
    ApiLog save(ApiLog entity);
}
