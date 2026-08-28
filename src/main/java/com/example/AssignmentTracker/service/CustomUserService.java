package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserService implements UserDetailsService {

    private final StudentRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student userAuth = userRepository.findByEmail(username).get();
        UserDetails userDetails = User.builder().username(userAuth.getEmail()).password(userAuth.getPassword()).roles(String.valueOf(userAuth.getRole())).build();

        return userDetails;


    }



}
