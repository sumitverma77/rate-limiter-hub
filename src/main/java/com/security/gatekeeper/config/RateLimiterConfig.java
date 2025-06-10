package com.security.gatekeeper.config;

import com.security.gatekeeper.limiter.RateLimiter;
import com.security.gatekeeper.limiter.impl.FixedWindowRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfig {

    private static final ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Bean
    public RateLimiter fixedWindowRateLimiter() {
        FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(10, 60000);
        rateLimiters.put("/test", rateLimiter);
        return rateLimiter;
    }

    public static RateLimiter getRateLimiter(String path) {
        return rateLimiters.get(path);
    }
}
