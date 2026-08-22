package com.example.AssignmentTracker.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RateLimit  extends OncePerRequestFilter {

    private final int MAX_REQUEST=5;
    private final long WINDOW_TIME=60;

    private final ConcurrentHashMap<String, Deque<Instant>> requests=new ConcurrentHashMap<>();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


      //  Client identify karo
         String ClientId=request.getRemoteAddr();
        // Current time
         Instant now=Instant.now();
        // Window ka starting time
         Instant WindowStart=now.minusSeconds(WINDOW_TIME);

        //  Client ki request timestamps wali Deque nikalo
        Deque<Instant> timestamps = requests.computeIfAbsent(ClientId, key -> new ConcurrentLinkedDeque<>()
        );

        // Window se bahar ki purani requests hatao
        while(!timestamps.isEmpty() && timestamps.peekFirst().isBefore(WindowStart)){
            timestamps.pollFirst();
        }

        // Check karo limit exceed hui ya nahi

        if(timestamps.size()>=MAX_REQUEST){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("""
                              "success": false,
                               "message": "Request limit exceeded for %s. Try again later.",
                               "data": null
                            
                            """,ClientId)
            );
            // Current request ka timestamp store karo

            timestamps.addLast(now);
            //  Request ko Controller ki taraf bhejo

            filterChain.doFilter(request,response);
        }



    }
}


