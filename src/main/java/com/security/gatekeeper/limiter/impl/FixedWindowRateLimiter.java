package com.security.gatekeeper.limiter.impl;

import com.security.gatekeeper.limiter.RateLimiter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeInMilliseconds;
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private long windowStartTime = System.currentTimeMillis();

    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMilliseconds) {
        this.maxRequests = maxRequests;
        this.windowSizeInMilliseconds = windowSizeInMilliseconds;
    }

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - windowStartTime > windowSizeInMilliseconds) {
            requestCounts.clear();
            windowStartTime = currentTime;
        }

        AtomicInteger requestCount = requestCounts.computeIfAbsent(clientId, k -> new AtomicInteger(0));

        if (requestCount.incrementAndGet() > maxRequests) {
            return false;
        }
        return true;
    }
}
