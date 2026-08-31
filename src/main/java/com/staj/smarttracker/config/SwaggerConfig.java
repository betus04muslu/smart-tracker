package com.staj.smarttracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{
    @Bean
    public OpenAPI customOpenAI(){
        return new OpenAPI()
                .info(new Info()
                .title("Smart Tracker API Dokümantasyonu")
                .version("1.0")
                .description("Çalışan efor ve yapay zeka destekli görev takip sistemi REST API uç noktaları."));

    }
}