package org.example.employeetimetrackingservice.controllers;

import org.example.employeetimetrackingservice.controllers.CookieCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CookieCheckFilter> loggingFilter() {
        FilterRegistrationBean<CookieCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CookieCheckFilter());
        registrationBean.addUrlPatterns("/app/*");  // Указание на пути, на которых фильтр должен срабатывать
        return registrationBean;
    }
}
