package ghojeong.image.documentation;

import ghojeong.common.Documentation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

final class FetchImageUploadUrlDocumentation extends Documentation {
    private static final String path = "/v1/images/upload-url";
    private static final String identifier = "fetch-image-upload-url";

    private ResponseFieldsSnippet createResponseSnippet() {
        return responseFields(
                fieldWithPath("data.imageUploadUrl").type(STRING).description("이미지를 업로드할 수 있는 URL"),
                fieldWithPath("data.imageUrl").type(STRING).description("업로드 후 이미지를 조회할 수 있는 URL")
        ).and(baseFields);
    }

    @DisplayName("GET /v1/images/upload-url 문서화")
    @Test
    void test() {
        ExtractableResponse<Response> actual = createRequestSpec(
                identifier,
                createResponseSnippet()
        ).header(AUTHORIZATION, getAccessToken())
                .when().get(path)
                .then().log().all().extract();

        assertThat(actual.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }
}
