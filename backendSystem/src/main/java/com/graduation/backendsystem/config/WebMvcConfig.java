package com.graduation.backendsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(BackendSystemProperties.class)
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("file:"+ backendSystemProperties.getImgaeResultPath());
//        registry.addResourceHandler("/image/**")
//                .addResourceLocations("file:D:/WorkSpace/graduation-project/application/result/imageResult/");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:"+ backendSystemProperties.getImgaeResultPath());

        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:"+ backendSystemProperties.getAudioResultPath());
        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:"+ backendSystemProperties.getVideoResultPath());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //支持跨域请求
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

}