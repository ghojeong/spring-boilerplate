package ghojeong.auth.acceptance;

import ghojeong.auth.dto.request.LogoutRequest;
import ghojeong.common.CommonFixture;

public final class LogoutFixture extends CommonFixture {
    private LogoutFixture() {}

    public static LogoutRequest createLogoutRequest() {
        return LogoutRequest.builder()
                .deviceId("550e8400-e29b-41d4-a716-446655440000")
                .build();
    }
}
