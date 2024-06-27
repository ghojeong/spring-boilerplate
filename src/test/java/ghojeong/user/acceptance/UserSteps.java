package ghojeong.user.acceptance;

import ghojeong.common.AcceptanceTestSteps;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public final class UserSteps extends AcceptanceTestSteps {

    private static final String PATH_USERS_ME = "/v1/users/me";

    private UserSteps() {}

    static ExtractableResponse<Response> fetchUser() {
        return requestAuthGet(PATH_USERS_ME)
                .statusCode(HttpStatus.OK.value()).extract();
    }

    static ExtractableResponse<Response> updateUser() {
        return requestAuthPut(PATH_USERS_ME, UserFixture.createUpdateRequest())
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static void initializeUser() {
        UserSteps.updateUser();
    }
}
