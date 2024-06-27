package ghojeong.auth.documentation;

import ghojeong.common.Documentation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

final class DeleteMyUserDocumentation extends Documentation {
    private static final String path = "/v1/users/me";
    private static final String identifier = "delete-my-user";

    private ResponseFieldsSnippet createResponseSnippet() {
        return responseFields(
                fieldWithPath("data.seq").type(NUMBER).description("Deprecated 사용되면 안된다."),
                fieldWithPath("data.userSeq").type(NUMBER).description("유저의 고유 번호")
        ).and(baseFields);
    }

    @DisplayName("DELETE /v1/users/me 문서화")
    @Test
    void test() {
        ExtractableResponse<Response> actual = createRequestSpec(
                identifier,
                createResponseSnippet()
        ).header(AUTHORIZATION, getAccessToken())
                .when().delete(path)
                .then().log().all().extract();

        assertThat(actual.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }
}
