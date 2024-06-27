package ghojeong.auth.documentation;

import ghojeong.auth.acceptance.AuthSteps;
import ghojeong.common.Documentation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

final class RefreshTokenDocumentation extends Documentation {
    private static final String path = "/v1/refresh-token";
    private static final String identifier = "refresh-token";

    private RequestFieldsSnippet createRequestSnippet() {
        return requestFields(
                fieldWithPath("refreshToken").type(STRING).description("accessToken 을 새로 발급 받기 위한 토큰")
        );
    }

    private ResponseFieldsSnippet createResponseSnippet() {
        return responseFields(
                fieldWithPath("data.seq").type(NUMBER).description("Deprecated 사용되면 안된다."),
                fieldWithPath("data.userSeq").type(NUMBER).description("유저의 고유 번호"),
                fieldWithPath("data.type").type(STRING).description("유저가 가입한 형태"),
                fieldWithPath("data.ssoId").type(STRING).description("유저를 식별할 수 있는 값"),
                fieldWithPath("data.accessToken").type(STRING).description("유저를 인증할 수 있는 토큰"),
                fieldWithPath("data.accessTokenExpiredAt").type(STRING).attributes(dateTimePattern).description("인증 토큰의 만료 일시")
        ).and(baseFields);
    }

    @DisplayName("POST /v1/refresh-token 문서화")
    @Test
    void test() {
        ExtractableResponse<Response> actual = createRequestSpec(
                identifier,
                createRequestSnippet(),
                createResponseSnippet()
        ).body(AuthSteps.createRefreshTokenRequest()).when().post(path)
                .then().log().all().extract();

        assertThat(actual.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());
    }
}
