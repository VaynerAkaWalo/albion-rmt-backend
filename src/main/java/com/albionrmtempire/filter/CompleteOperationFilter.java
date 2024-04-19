package com.albionrmtempire.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;

@Log4j2
@Order(0)
@Component
@RequiredArgsConstructor
public class CompleteOperationFilter extends OncePerRequestFilter {

    private final Clock clock;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final long startTime = clock.millis();
        filterChain.doFilter(request, response);
        log.info("Endpoint {}, Method: {}, Time: {}ms", trimPath(request), request.getMethod(), clock.millis() - startTime);
    }

    private String trimPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }
}
