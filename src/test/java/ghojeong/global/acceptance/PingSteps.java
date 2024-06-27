package ghojeong.global.acceptance;

import ghojeong.common.AcceptanceTestSteps;
import org.springframework.http.HttpStatus;

public final class PingSteps extends AcceptanceTestSteps {
    private static final String PATH_FOLLOW = "/oing";

    private PingSteps() {}

    public static String ping() {
        return requestGet("/ping")
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().prettyPrint();
    }
}
