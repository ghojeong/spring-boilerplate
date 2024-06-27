package ghojeong.common.controller;

import ghojeong.common.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response
    ) {
        if (!(body instanceof CommonResponse<?> commonResponse)) {
            return body;
        }
        response.setStatusCode(commonResponse.getHttpStatus());
        return commonResponse.setEndpoint(getEndpoint(request));
    }

    private String getEndpoint(ServerHttpRequest request) {
        return String.format(
                "%s %s",
                request.getMethod().name(),
                request.getURI().getPath()
        );
    }
}
