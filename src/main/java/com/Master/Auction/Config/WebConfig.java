package com.Master.Auction.Config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import com.Master.Auction.Interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    //이거 작동 됨 -> 이전 프로젝트에서는 안되었는데 단순한 오류였던 듯
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/assets/**", "/css/**", "/js/**", "/images/**",
                        "/api/v1/email/send", "/api/v1/email/verify","/Member/login", "/Member/save", "/Member/email-check", "/");
    }
}
