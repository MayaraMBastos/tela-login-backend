package com.example.tela_login.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Definindo quais origens são permitidas (localhost:3000)
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*"); // Permite todos os métodos (GET, POST, etc.)
        config.addAllowedHeader("*"); // Permite todos os cabeçalhos

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
