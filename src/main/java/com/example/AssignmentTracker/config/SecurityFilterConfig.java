package com.example.AssignmentTracker.config;
import com.example.AssignmentTracker.customjwt.JwtService;
import com.example.AssignmentTracker.filter.JwtAuthFilter;
import com.example.AssignmentTracker.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityFilterConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtService jwtService,
                                                   CustomUserService customUserService,
                                                   AuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        JwtAuthFilter jwtAuthenticationFilter=new JwtAuthFilter(jwtService,customUserService);
        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers("/api/students/login","/api/admin/add/{adminId}").permitAll()
                .requestMatchers("/api/students/**","/api/studentss/**").hasRole("STUDENT")
                        .anyRequest().authenticated());
 httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
         httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return  new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"Status\":401,\"error\":\"Unauthorized\"," + "\"" +
                    "message\":\"Missing or invalid bearer token }");

        };


    }
}


