package com.security.gatekeeper.filter;

import com.security.gatekeeper.limiter.RateLimiter;
import com.security.gatekeeper.config.RateLimiterConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimiterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String clientId = httpRequest.getRemoteAddr();
        System.out.println(clientId);
        RateLimiter rateLimiter = RateLimiterConfig.getRateLimiter(httpRequest.getRequestURI());
        if (rateLimiter != null && rateLimiter.allowRequest(clientId)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Too many requests");
        }
    }
}
