package ghojeong.auth.config;

import ghojeong.auth.filter.JwtAuthFilter;
import ghojeong.auth.filter.LoggingFilter;
import ghojeong.auth.handler.LoggingAccessDeniedHandler;
import ghojeong.auth.handler.LoggingAuthenticationEntryPoint;
import ghojeong.auth.service.JwtAuthProvider;
import ghojeong.auth.service.PasswordAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@EnableJpaAuditing
public class AuthConfig {
    @Bean
    public AuthenticationManager createAuthManager(
            PasswordAuthProvider passwordAuthProvider,
            JwtAuthProvider jwtAuthProvider
    ) {
        return new ProviderManager(passwordAuthProvider, jwtAuthProvider);
    }

    @Bean
    public SecurityFilterChain createFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthFilter jwtAuthFilter,
            LoggingFilter loggingFilter,
            LoggingAuthenticationEntryPoint authenticationEntryPoint,
            LoggingAccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        return httpSecurity.headers(
                it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        ).csrf(
                AbstractHttpConfigurer::disable
        ).cors(
                it -> it.configurationSource(corsConfigurationSource())
        ).httpBasic(
                AbstractHttpConfigurer::disable
        ).formLogin(
                AbstractHttpConfigurer::disable
        ).sessionManagement(
                it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).exceptionHandling(
                it -> it.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
        ).requestCache(
                it -> it.configure(httpSecurity)
        ).addFilterBefore(
                jwtAuthFilter, HeaderWriterFilter.class
        ).addFilterBefore(
                loggingFilter, JwtAuthFilter.class
        ).authorizeHttpRequests(
                it -> it.anyRequest().permitAll()
        ).build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        // NOTE: https://docs.spring.io/spring-security/reference/reactive/integrations/cors.html
        // NOTE: https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html#mvc-cors-filter
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
