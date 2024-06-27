package ghojeong.common;

import ghojeong.admin.dto.request.AdminLoginRequest;
import ghojeong.auth.dto.request.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static ghojeong.common.util.DateTimeParser.LOCAL_TIME_ZONE;
import static ghojeong.common.util.DateTimeParser.TIME_ZONE_HEADER;

public abstract class AcceptanceTestSteps {
    protected static ValidatableResponse requestGet(String path) {
        return given().when().get(path).then().log().all();
    }


    protected static ValidatableResponse requestAuthGet(String path) {
        return givenAuth().when().get(path).then().log().all();
    }

    protected static ValidatableResponse requestPost(String path, Object body) {
        return given().body(body).when().post(path).then().log().all();
    }

    protected static ValidatableResponse requestAuthPost(String path, Object body) {
        return givenAuth().body(body).when().post(path).then().log().all();
    }

    protected static ValidatableResponse requestSuperAdminPost(String path, Object body) {
        return givenSuperAdminAuth().body(body).when().post(path).then().log().all();
    }

    protected static ValidatableResponse requestPut(String token, String path, Object body) {
        return given().auth().oauth2(token).body(body).when().put(path).then().log().all();
    }

    protected static ValidatableResponse requestAuthPut(String path, Object body) {
        return givenAuth().body(body).when().put(path).then().log().all();
    }

    protected static ValidatableResponse requestSuperAdminPut(String path, Object body) {
        return givenSuperAdminAuth().body(body).when().put(path).then().log().all();
    }

    protected static ValidatableResponse requestAuthDelete(String path) {
        return givenAuth().when().delete(path).then().log().all();
    }

    protected static ValidatableResponse requestAuthDelete(String path, Object body) {
        return givenAuth().body(body).when().delete(path).then().log().all();
    }

    protected static ValidatableResponse requestSuperAdminDelete(String path, Object body) {
        return givenSuperAdminAuth().body(body).when().delete(path).then().log().all();
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all()
                .header(TIME_ZONE_HEADER, LOCAL_TIME_ZONE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);
    }

    protected static RequestSpecification givenAuth(String token) {
        return given().auth().oauth2(token);
    }

    private static RequestSpecification givenAuth(SignUpRequest request) {
        return givenAuth(getAccessToken(request));
    }

    public static RequestSpecification givenAuth() {
        return givenAuth(getAccessToken());
    }

    private static RequestSpecification givenSuperAdminAuth() {
        return givenAuth(getSuperAdminAccessToken());
    }

    public static String getAccessToken(SignUpRequest request) {
        return requestSignUp(request).jsonPath()
                .getString("data.accessToken");
    }

    static String getAccessToken() {
        return getAccessToken(
                CommonFixture.createSignUpRequest()
        );
    }

    static String getSuperAdminAccessToken() {
        AdminLoginRequest request = CommonFixture.createSuperAdminLoginRequest();
        ExtractableResponse<Response> response = requestPost("/v1/admin/login", request)
                .statusCode(HttpStatus.CREATED.value()).extract();
        return response.jsonPath().getString("data.accessToken");
    }

    protected static ExtractableResponse<Response> requestSignUp(SignUpRequest request) {
        return requestPost("/v1/signup", request)
                .statusCode(HttpStatus.CREATED.value()).extract();
    }
}
