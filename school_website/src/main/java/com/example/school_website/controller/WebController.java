package com.example.school_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


    @Controller
    public class WebController {

        @GetMapping("/")
        public String home() {
            // This looks inside src/main/resources/templates/ for "index.html"
            return "index";
        }

        @GetMapping("/login")
        public String login() {
            // This looks inside src/main/resources/templates/ for "login.html"
            return "login";
        }
    }
