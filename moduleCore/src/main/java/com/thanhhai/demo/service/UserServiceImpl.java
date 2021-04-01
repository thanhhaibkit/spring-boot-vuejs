package com.thanhhai.demo.service;

import com.thanhhai.demo.dto.UserDto;
import com.thanhhai.demo.entity.User;
import com.thanhhai.demo.repository.RoleRepository;
import com.thanhhai.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) {
        return UserDto.from(userRepository.save(userDto.toEntity()));
    }

    @Override
    public List<UserDto> getAll() {
        final List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }

    @Override
    public UserDto getById(final Long id) {

        return UserDto.from(userRepository.findById(id).orElse(null));
    }
}
