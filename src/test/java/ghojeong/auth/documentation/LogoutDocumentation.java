package ghojeong.auth.documentation;

import ghojeong.auth.acceptance.LogoutFixture;
import ghojeong.common.Documentation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

final class LogoutDocumentation extends Documentation {
    private static final String path = "/v1/logout";
    private static final String identifier = "logout";

    private RequestFieldsSnippet createRequestSnippet() {
        return requestFields(
                fieldWithPath("deviceId").type(STRING).description("디바이스를 식별하기 위해 로컬에서 발급한 문자열"),
                fieldWithPath("deviceUUID").type(NULL).description("Deprecated 사용되면 안된다.")
        );
    }

    private ResponseFieldsSnippet createResponseSnippet() {
        return responseFields(
                fieldWithPath("data.userSeq").type(NUMBER).description("로그아웃 된 유저의 식별번호")
        ).and(baseFields);
    }


    @DisplayName("POST /v1/logout 문서화")
    @Test
    void test() {
        ExtractableResponse<Response> actual = createRequestSpec(
                identifier,
                createRequestSnippet(),
                createResponseSnippet()
        ).header(AUTHORIZATION, getAccessToken())
                .body(LogoutFixture.createLogoutRequest())
                .when().post(path)
                .then().log().all().extract();

        assertThat(actual.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }
}
