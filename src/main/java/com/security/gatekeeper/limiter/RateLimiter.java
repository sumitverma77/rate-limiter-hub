package com.security.gatekeeper.limiter;

public interface RateLimiter {

    boolean allowRequest(String clientId);
}
