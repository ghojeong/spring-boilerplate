package ghojeong.auth.filter;

import ghojeong.log.service.ApiLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoggingFilter extends OncePerRequestFilter {
    private final ApiLogService apiLogService;

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        final String path = request.getServletPath();
        return !path.startsWith("/v")
                || path.startsWith("/login");
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws IOException {
        final long start = System.currentTimeMillis();
        final ContentCachingRequestWrapper cachedRequest =
                new ContentCachingRequestWrapper(request);
        final ContentCachingResponseWrapper cachedResponse =
                new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(cachedRequest, cachedResponse);
        } catch (final Exception e) {
            logger.error(e);
        } finally {
            final long duration = System.currentTimeMillis() - start;
            apiLogService.saveApiLog(
                    cachedRequest,
                    cachedResponse,
                    duration
            );
        }
        cachedResponse.copyBodyToResponse();
    }
}
