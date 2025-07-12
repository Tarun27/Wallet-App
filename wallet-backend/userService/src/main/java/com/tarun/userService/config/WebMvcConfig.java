package com.tarun.userService.config;

import com.tarun.userService.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registers the HttpLoggingInterceptor for business endpoints
 * while leaving infrastructure routes (Swagger, Actuator, etc.) untouched.
 */
@Configuration
//@Profile("dev")        // ← optional: only enable in the dev profile
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpLoggingInterceptor httpLoggingInterceptor;

    // prefer constructor injection – easier for tests, no proxy timing issues
    public WebMvcConfig(HttpLoggingInterceptor httpLoggingInterceptor) {
        this.httpLoggingInterceptor = httpLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLoggingInterceptor)

                // your real API
                .addPathPatterns("/users/**")

                // exclude infrastructure endpoints so they aren’t blocked
                .excludePathPatterns(
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/actuator/**"
                );
    }
}
