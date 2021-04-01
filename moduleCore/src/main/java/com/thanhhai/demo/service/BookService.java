package com.thanhhai.demo.service;

import com.thanhhai.demo.entity.Book;
import com.thanhhai.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book user) {
        return bookRepository.save(user);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
