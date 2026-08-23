package com.example.AssignmentTracker.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/stu")
public class BasicController {

    @GetMapping("/test")
    public String test() {

        System.out.println("========== TEST CONTROLLER HIT ==========");

        return "HELLO STUDENT CONTROLLER";
    }

    @GetMapping("/sakshi-test-123")
    public String test1() {

        System.out.println("========== TEST CONTROLLER HIT ==========");

        return "MY CURRENT APPLICATION";
    }
}