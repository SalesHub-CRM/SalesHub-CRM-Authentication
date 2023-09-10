package com.example.CRM.Authentication.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Replace with your React app's URL
                .allowedMethods("GET", "POST", "OPTIONS","PUT","DELETE") // Adjust allowed methods
                .allowedHeaders("*") // Adjust allowed headers
                .allowCredentials(true) // Allow credentials
                .maxAge(3600); // Set max age if needed
    }
}*/
