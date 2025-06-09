package com.security.gatekeeper.config;

import com.security.gatekeeper.limiter.RateLimiter;
import com.security.gatekeeper.limiter.impl.FixedWindowRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter rateLimiter() {
        return new FixedWindowRateLimiter(10, 60000);
    }
}
