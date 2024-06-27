package ghojeong.global.acceptance;

import ghojeong.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PingAcceptanceTest extends AcceptanceTest {
    @DisplayName("Ping 을 하면 pong 을 응답해야 한다.")
    @Test
    void ping() {
        assertThat(PingSteps.ping())
                .isEqualTo("pong");
    }
}
