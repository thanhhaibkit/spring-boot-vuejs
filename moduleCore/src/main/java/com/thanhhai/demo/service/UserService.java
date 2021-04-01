package com.thanhhai.demo.service;

import com.thanhhai.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);

    List<UserDto> getAll();

    UserDto getById(final Long id);
}
