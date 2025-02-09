package com.Master.Auction.Config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionFileConfig implements WebMvcConfigurer {

    private String resourcePath = "/Question/**";
    private String savePath = "file:///C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/Question/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

}

