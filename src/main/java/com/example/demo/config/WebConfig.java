package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : christiaan.griffioen
 * @since :  24-6-2021, do
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${base.path}")
    private String basePath;

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(basePath + "/*")
                .allowedOrigins(theAllowedOrigins)
                .allowedMethods("*");
    }


}
