package com.xyw.config;

import com.xyw.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/6/17
 * Time:00:04
 */
@Configuration
// Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，
// 作用为：配置spring容器(应用上下文)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    AccessInterceptor accessInterceptor;

    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }
}
