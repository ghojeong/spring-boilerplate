package ghojeong.auth.filter;

import ghojeong.auth.domain.JwtAuthToken;
import ghojeong.auth.util.JsonMapper;
import ghojeong.common.dto.response.ExceptionResponse;
import ghojeong.common.exception.ForbiddenException;
import ghojeong.common.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JsonMapper jsonMapper;

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
        try {
            attemptAuthentication(request);
            filterChain.doFilter(request, response);
        } catch (final UnauthorizedException e) {
            writeResponse(response, e, UNAUTHORIZED);
        } catch (final ForbiddenException e) {
            writeResponse(response, e, FORBIDDEN);
        } catch (final Exception e) {
            logger.error(e);
            writeResponse(response, e, INTERNAL_SERVER_ERROR);
        }
    }

    private void attemptAuthentication(
            final HttpServletRequest request
    ) {
        final String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isBlank()) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(
                authenticationManager.authenticate(new JwtAuthToken(jwt))
        );
    }

    private void writeResponse(
            final HttpServletResponse response,
            final Exception exception,
            final HttpStatus httpStatus
    ) throws IOException {
        response.setStatus(httpStatus.value());
        jsonMapper.writeAsJson(
                response,
                new ExceptionResponse<>(httpStatus, exception)
        );
    }
}
