package com.thanhhai.demo.dto;

import com.thanhhai.demo.entity.User;
import com.thanhhai.demo.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private String emailAddress;
    private String phoneNumber;
    private Long createdBy;
    private Long updatedBy;

    private ERole role;

    public User toEntity() {
        User entity = new User();

        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setLastName(this.getLastname());
        entity.setFirstName(this.getFirstname());
        entity.setEmailAddress(this.getEmailAddress());
        entity.setPhoneNumber(this.getPhoneNumber());

        return entity;
    }

    public static UserDto from(final User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .lastname(user.getLastName())
            .firstname(user.getFirstName())
            .emailAddress(user.getEmailAddress())
            .phoneNumber(user.getPhoneNumber())
            .build();
    }
}
