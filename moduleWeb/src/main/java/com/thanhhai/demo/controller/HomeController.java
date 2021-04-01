package com.thanhhai.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @GetMapping()
    public String home()
    {
        return "home";
    }


    @GetMapping("/hello")
    public String hello()
    {
        return "hello";
    }
}
