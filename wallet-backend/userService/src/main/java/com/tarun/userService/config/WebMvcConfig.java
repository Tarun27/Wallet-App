package com.tarun.userService.config;

import com.tarun.userService.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry; // <-- import
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registers the HttpLoggingInterceptor for business endpoints
 * while leaving infrastructure routes (Swagger, Actuator, etc.) untouched.
 * Also configures global CORS for all endpoints.
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
                .addPathPatterns("/users/**")
                .excludePathPatterns(
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/actuator/**"
                );
    }

    // ---- ADD THIS METHOD ----
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true);
    }
}
