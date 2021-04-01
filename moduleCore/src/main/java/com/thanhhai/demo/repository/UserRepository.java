package com.thanhhai.demo.repository;

import com.thanhhai.demo.entity.Book;
import com.thanhhai.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}