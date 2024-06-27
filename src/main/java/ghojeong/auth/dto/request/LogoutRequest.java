package ghojeong.auth.dto.request;

import lombok.Builder;

@Builder
public record LogoutRequest(
        String deviceId,
        String deviceUUID
) {
    @Override
    public String deviceId() {
        return deviceId != null && !deviceId.isBlank()
                ? deviceId : deviceUUID;
    }
}
