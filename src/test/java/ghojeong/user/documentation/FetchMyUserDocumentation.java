package ghojeong.user.documentation;

import ghojeong.common.Documentation;
import ghojeong.user.acceptance.UserSteps;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

final class FetchMyUserDocumentation extends Documentation {
    private static final String path = "/v1/users/me";
    private static final String identifier = "fetch-my-user";

    private ResponseFieldsSnippet createResponseSnippet() {
        return responseFields(
                fieldWithPath("data.seq").type(NUMBER).description("Deprecated 사용되면 안된다."),
                fieldWithPath("data.userSeq").type(NUMBER).description("유저의 고유 번호"),
                fieldWithPath("data.type").type(STRING).description("유저가 가입한 형태"),
                fieldWithPath("data.ssoId").type(STRING).description("유저를 식별할 수 있는 값"),
                fieldWithPath("data.email").optional().type(STRING).description("유저의 이메일"),
                fieldWithPath("data.nickname").type(STRING).description("유저의 별명"),
                fieldWithPath("data.image").type(STRING).description("유저의 이미지를 위한 url 값"),
                fieldWithPath("data.biography").optional().type(STRING).description("유저의 자기소개"),
                fieldWithPath("data.tags").type(ARRAY).description("유저에 붙은 태그들"),
                fieldWithPath("data.morningReminderTime").type(STRING).description("오전의 알림 시간 (HH:mm)"),
                fieldWithPath("data.eveningReminderTime").type(STRING).description("오후의 알림 시간 (HH:mm)"),
                fieldWithPath("data.isMorningReminderActive").type(BOOLEAN).description("오전 알림의 활성화 여부"),
                fieldWithPath("data.isEveningReminderActive").type(BOOLEAN).description("오후 알림의 활성화 여부"),
                fieldWithPath("data.isPrivateAccount").type(BOOLEAN).description("계정의 공개 여부"),
                fieldWithPath("data.offlineUpdatedAt").optional().type(STRING).description("계정이 디바이스에서 수정된 일시")
        ).and(baseFields);
    }

    @DisplayName("GET /v1/users/me 문서화")
    @Test
    void test() {
        UserSteps.initializeUser();
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
