package com.security.gatekeeper.config;

import com.security.gatekeeper.filter.RateLimiterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import com.security.gatekeeper.limiter.RateLimiter;

@Configuration
public class WebConfig {

    @Autowired
    private RateLimiter rateLimiter;

    @Bean
    public FilterRegistrationBean<RateLimiterFilter> filterRegistrationBean() {
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimiterFilter(rateLimiter));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
