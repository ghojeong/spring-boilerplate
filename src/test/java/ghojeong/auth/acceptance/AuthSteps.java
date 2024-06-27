package ghojeong.auth.acceptance;

import ghojeong.auth.dto.request.RefreshTokenRequest;
import ghojeong.common.AcceptanceTestSteps;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public final class AuthSteps extends AcceptanceTestSteps {
    private AuthSteps() {}

    static ExtractableResponse<Response> signUp() {
        return requestPost(
                "/v1/signup",
                AuthFixture.createSignUpRequest()
        ).statusCode(HttpStatus.CREATED.value()).extract();
    }

    static ExtractableResponse<Response> deleteMyUser() {
        return requestAuthDelete("/v1/users/me")
                .statusCode(HttpStatus.OK.value()).extract();
    }

    static ExtractableResponse<Response> unauthorizedLogIn() {
        return requestPost(
                "/v1/login",
                AuthFixture.createLogInRequest()
        ).statusCode(HttpStatus.UNAUTHORIZED.value()).extract();
    }

    static ExtractableResponse<Response> login() {
        signUp();
        return requestPost(
                "/login",
                AuthFixture.createLoginRequest()
        ).statusCode(HttpStatus.CREATED.value()).extract();
    }

    static ExtractableResponse<Response> loginFail() {
        return requestPost(
                "/login",
                AuthFixture.createLoginRequest()
        ).statusCode(HttpStatus.FORBIDDEN.value()).extract();
    }

    static ExtractableResponse<Response> emptyLoginFail() {
        signUp();
        return requestPost(
                "/login",
                AuthFixture.createEmptyLoginRequest()
        ).statusCode(HttpStatus.FORBIDDEN.value()).extract();
    }

    static ExtractableResponse<Response> invalidPasswordLoginFail() {
        signUp();
        return requestPost(
                "/login",
                AuthFixture.createInvalidPasswordLoginRequest()
        ).statusCode(HttpStatus.FORBIDDEN.value()).extract();
    }

    static ExtractableResponse<Response> refreshToken() {
        return requestAuthPost(
                "/v1/refresh-token",
                createRefreshTokenRequest()
        ).statusCode(HttpStatus.CREATED.value()).extract();
    }

    static ExtractableResponse<Response> refreshTokenFail() {
        return requestAuthPost(
                "/v1/refresh-token",
                new RefreshTokenRequest("")
        ).statusCode(HttpStatus.FORBIDDEN.value()).extract();
    }

    public static RefreshTokenRequest createRefreshTokenRequest() {
        String refreshToken = signUp().jsonPath().getString("data.refreshToken");
        return new RefreshTokenRequest(refreshToken);
    }
}
