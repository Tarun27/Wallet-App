package com.tarun.walletService.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // apply to all endpoints
                .addMapping("/**")
                // allow your React dev origin
                .allowedOrigins("http://localhost:5173")
                // allow the methods you use
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // allow any headers (including Authorization)
                .allowedHeaders("*")
                // expose Authorization header back to the client if needed
                .exposedHeaders("Authorization")
                // allow credentials (cookies or auth headers)
                .allowCredentials(true)
                // how long to cache pre‑flight (in seconds)
                .maxAge(3600);
    }
}
