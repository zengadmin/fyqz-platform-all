package com.fyqz.config;

import com.fyqz.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class TokenConfig extends WebMvcConfigurerAdapter {
    @Bean
    public TokenInterceptor getTokenInterceptor(){
        TokenInterceptor tokenInterceptor=new TokenInterceptor();
        return tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor());
        super.addInterceptors(registry);
    }
}