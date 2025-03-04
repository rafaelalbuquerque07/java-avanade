package com.java_avanade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração "CORS" (Cross-Origin Resource Sharing) para permitir acesso entre domínios.
 */
@Configuration
public class CorsConfig {

    /**
     * Configuração global de "CORS" para todos os endpoints da API.
     * Permite todos os métodos HTTP (GET, POST, PUT, DELETE) de qualquer origem.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .maxAge(3600);
            }
        };
    }

    /**
     * Bean de filtro "CORS" para controle em nível de solicitação HTTP.
     * Configuração mais detalhada para casos específicos.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciais como "cookies"
        config.setAllowCredentials(true);

        // Permitir origens específicas (para produção, substitua por suas origens reais)
        config.addAllowedOrigin("http://localhost:3000"); // Frontend React típico
        config.addAllowedOrigin("http://localhost:8080"); // Para desenvolvimento

        // Para ambiente de produção, adicione o seu domínio
        // config.addAllowedOrigin("https://seu-dominio.com");

        // Permitir todos os cabeçalhos e métodos
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Aplicar configuração a todos os endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}