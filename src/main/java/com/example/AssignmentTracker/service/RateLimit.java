package com.example.AssignmentTracker.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
@Service
public class RateLimit extends OncePerRequestFilter {

    private final int MAX_REQUEST = 5;
    private final long WINDOW_TIME = 60;

    private final ConcurrentHashMap<String, Deque<Instant>> requests = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientId = request.getRemoteAddr();
        Instant now = Instant.now();
        Instant windowStart = now.minusSeconds(WINDOW_TIME);

        Deque<Instant> timestamps = requests.computeIfAbsent(clientId, key -> new ConcurrentLinkedDeque<>());

        // Remove old requests outside the window
        while (!timestamps.isEmpty() && timestamps.peekFirst().isBefore(windowStart)) {
            timestamps.pollFirst();
        }

        if (timestamps.size() >= MAX_REQUEST) {
            // Block request
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("""
                    {
                      "success": false,
                      "message": "Request limit exceeded for %s. Try again later.",
                      "data": null
                    }
                    """, clientId)
            );
            return;
        }


        timestamps.addLast(now);
        filterChain.doFilter(request, response);
    }
}
