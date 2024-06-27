package ghojeong.auth.filter;

import ghojeong.auth.domain.JwtAuthToken;
import ghojeong.auth.util.JsonMapper;
import ghojeong.common.dto.response.ExceptionResponse;
import ghojeong.common.exception.ForbiddenException;
import ghojeong.common.exception.UnauthorizedException;
import ghojeong.log.service.ApiLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JsonMapper jsonMapper;
    private final ApiLogService apiLogService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/v")
                || path.startsWith("/login");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final long start = System.currentTimeMillis();
        ContentCachingRequestWrapper cachedRequest =
                new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachedResponse =
                new ContentCachingResponseWrapper(response);
        doFilter(cachedRequest, cachedResponse, filterChain);
        final long duration = System.currentTimeMillis() - start;
        try {
            apiLogService.saveApiLog(
                    cachedRequest,
                    cachedResponse,
                    duration
            );
        } catch (Exception e) {
            logger.error(e);
        }
        cachedResponse.copyBodyToResponse();
    }

    private void doFilter(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            attemptAuthentication(request);
        } catch (UnauthorizedException e) {
            writeResponse(response, e, UNAUTHORIZED);
        } catch (ForbiddenException e) {
            writeResponse(response, e, FORBIDDEN);
        } catch (Exception e) {
            writeResponse(response, e, INTERNAL_SERVER_ERROR);
        }
        filterChain.doFilter(request, response);
    }

    private void attemptAuthentication(
            ContentCachingRequestWrapper request
    ) {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isBlank()) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(
                authenticationManager.authenticate(new JwtAuthToken(jwt))
        );
    }

    private void writeResponse(
            ContentCachingResponseWrapper response,
            Exception exception,
            HttpStatus httpStatus
    ) throws IOException {
        response.setStatus(httpStatus.value());
        jsonMapper.writeAsJson(
                response,
                new ExceptionResponse<>(httpStatus, exception)
        );
    }
}
