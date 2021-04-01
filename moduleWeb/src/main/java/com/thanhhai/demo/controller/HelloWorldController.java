package com.thanhhai.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hi")
    public String helloWorld() {
        return "Hello World!";
    }
}
