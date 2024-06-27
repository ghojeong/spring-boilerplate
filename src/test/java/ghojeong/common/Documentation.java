package ghojeong.common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;

import java.util.List;

import static ghojeong.common.util.DateTimeParser.DATETIME_PATTERN;
import static ghojeong.common.util.DateTimeParser.DATE_PATTERN;
import static ghojeong.common.util.DateTimeParser.TIME_PATTERN;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;

@ExtendWith(RestDocumentationExtension.class)
public abstract class Documentation extends AcceptanceTest {
    protected static final String AUTHORIZATION = "Authorization";
    protected static final Attributes.Attribute dateTimePattern = key("format").value(DATETIME_PATTERN);
    protected static final Attributes.Attribute datePattern = key("format").value(DATE_PATTERN);
    protected static final Attributes.Attribute timePattern = key("format").value(TIME_PATTERN);
    protected static final List<FieldDescriptor> baseFields = List.of(
            fieldWithPath("apiVersion").type(STRING).description("API 의 버전"),
            fieldWithPath("timestamp").type(STRING).attributes(dateTimePattern).description("API 가 호출되어 응답한 일시"),
            fieldWithPath("endpoint").type(STRING).description("API 의 HTTP Method 와 URI"),
            fieldWithPath("httpStatus").type(STRING).description("HTTP Status 를 설명하는 문구"),
            fieldWithPath("code").type(NUMBER).description("HTTP Status Code 숫자값"),
            fieldWithPath("message").type(STRING).description("API 에서 일어난 일에 대한 메시지")
    );
    protected static final List<FieldDescriptor> listFields = List.of(
            fieldWithPath("count").type(NUMBER).description("API 에서 가져온 데이터 목록의 크기"),
            fieldWithPath("totalCount").type(NUMBER).description("DB에 존재하는 데이터 목록의 총 크기")
    );
    protected static final List<FieldDescriptor> noOffsetFields = List.of(
            fieldWithPath("count").type(NUMBER).description("API 에서 가져온 데이터 목록의 크기"),
            fieldWithPath("totalCount").type(NUMBER).description("DB에 존재하는 데이터 목록의 총 크기"),
            fieldWithPath("globalMaxSeq").type(NUMBER).description("DB에 존재하는 데이터 Seq 의 최댓값"),
            fieldWithPath("globalMinSeq").type(NUMBER).description("DB에 존재하는 데이터 Seq 의 최솟값")
    );
    private static final OperationPreprocessor headerPreprocessor = modifyHeaders()
            .remove("Transfer-Encoding")
            .remove("Date")
            .remove("Keep-Alive")
            .remove("Connection")
            .remove("Content-Length")
            .remove("Date")
            .remove("Vary")
            .remove("Host")
            .remove("Set-Cookie")
            .remove("Accept")
            .remove("X-Content-Type-Options")
            .remove("X-XSS-Protection")
            .remove("Cache-Control")
            .remove("Pragma")
            .remove("Expires")
            .remove("X-Frame-Options");
    private static final OperationPreprocessor prettyPreprocessor = prettyPrint();
    private static final OperationRequestPreprocessor requestPreprocessor = preprocessRequest(
            headerPreprocessor,
            prettyPreprocessor
    );
    private static final OperationResponsePreprocessor responsePreprocessor = preprocessResponse(
            headerPreprocessor,
            prettyPreprocessor
    );
    private RequestSpecification spec;

    @BeforeEach
    public void setUpDocSpec(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder().addFilter(
                documentationConfiguration(restDocumentation)
        ).build();
    }

    protected RequestSpecification createRequestSpec(String identifier, Snippet... snippets) {
        return RestAssured.given(spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(document(
                        identifier,
                        requestPreprocessor,
                        responsePreprocessor,
                        snippets
                ));
    }

    protected String getAccessToken() {
        return AcceptanceTestSteps.getAccessToken();
    }

    protected String getSuperAdminAccessToken() {
        return AcceptanceTestSteps.getSuperAdminAccessToken();
    }
}
