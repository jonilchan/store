package com.cy.store.config;


import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//拦截器的注册
@Configuration
public class LoginInterceptorConfigure implements WebMvcConfigurer {

    //拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        //配置白名单
        List<String> whiteList = new ArrayList<>();
        whiteList.add(("/bootstrap3/**"));
        whiteList.add("/css/**");
        whiteList.add("/images/**");
        whiteList.add("/js/**");
        whiteList.add("/web/register.html");
        whiteList.add("/web/login.html");
        whiteList.add("/web/index.html");
        whiteList.add("/web/product.html");
        whiteList.add("/users/reg");
        whiteList.add("/users/login");
        whiteList.add("/districts/**");
        whiteList.add("/products/**");
        whiteList.add("/druid/**");

        //拦截器注册
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(whiteList);

    }
}
