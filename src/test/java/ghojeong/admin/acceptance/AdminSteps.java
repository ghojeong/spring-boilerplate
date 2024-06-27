package ghojeong.admin.acceptance;

import ghojeong.common.AcceptanceTestSteps;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

final class AdminSteps extends AcceptanceTestSteps {

    private AdminSteps() {}

    static ExtractableResponse<Response> createAdmin() {
        return requestSuperAdminPost(
                "/v1/admin",
                AdminFixture.createAdminCreateRequest()
        ).statusCode(HttpStatus.CREATED.value()).extract();
    }

    static ExtractableResponse<Response> deleteAdmin() {
        createAdmin();
        return requestSuperAdminDelete(
                "/v1/admin",
                AdminFixture.createAdminDeleteRequest()
        ).statusCode(HttpStatus.OK.value()).extract();
    }

    static ExtractableResponse<Response> loginAdmin() {
        createAdmin();
        return requestSuperAdminPost(
                "/v1/admin/login",
                AdminFixture.createAdminLoginRequest()
        ).statusCode(HttpStatus.CREATED.value()).extract();
    }

    static ExtractableResponse<Response> updateAdminNickname(String nickname) {
        return requestPut(
                getAdminAccessToken(),
                "/v1/admin/me/nickname",
                AdminFixture.createAdminUpdateNicknameRequest(nickname)
        ).statusCode(HttpStatus.OK.value()).extract();
    }

    static ExtractableResponse<Response> updateAdminPassword(String password) {
        return requestPut(
                getAdminAccessToken(),
                "/v1/admin/me/password",
                AdminFixture.createAdminUpdatePasswordRequest(password)
        ).statusCode(HttpStatus.OK.value()).extract();
    }

    static ValidatableResponse updateAdminPermission(List<String> permission) {
        createAdmin();
        return requestSuperAdminPut(
                "/v1/admin/permission",
                AdminFixture.createAdminUpdatePermissionRequest(permission)
        );
    }

    private static String getAdminAccessToken() {
        return loginAdmin().jsonPath().getString("data.accessToken");
    }
}
