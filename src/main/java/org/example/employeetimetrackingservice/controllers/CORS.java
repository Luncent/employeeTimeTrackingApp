package org.example.employeetimetrackingservice.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORS implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Применить ко всем путям
                .allowedOrigins(
                        "http://localhost:8080",  // Разрешить запросы с localhost:8080
                        "http://127.0.0.1:5500",  // Разрешить запросы с localhost:5500 (или 127.0.0.1:5500)
                        "http://localhost",       // Разрешить запросы с localhost
                        "http://127.0.0.1:8080"   // Разрешить запросы с другого порта на localhost
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Разрешить различные HTTP методы
                .allowedHeaders("*")  // Разрешить все заголовки
                .allowCredentials(true);  // Разрешить отправку cookies (если нужно)
    }
}
