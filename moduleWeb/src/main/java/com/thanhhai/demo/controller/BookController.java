package com.thanhhai.demo.controller;

import com.thanhhai.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public String getAll(Model model)
    {
        model.addAttribute("books", bookService.findAll());
        return "books/all";
    }


}
