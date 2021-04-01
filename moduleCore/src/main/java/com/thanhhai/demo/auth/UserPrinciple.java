package com.thanhhai.demo.auth;

import com.thanhhai.demo.entity.User;
import com.thanhhai.demo.enums.ERole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserPrinciple implements UserDetails {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;
    private List<ERole> roles;

    @SneakyThrows
    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ERole> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getUserRoles())) {
            authorities = user.getUserRoles().stream().map(
                userRole -> new SimpleGrantedAuthority(userRole.getRole().getName().name())
            ).collect(Collectors.toList());

            roles =  user.getUserRoles().stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
        }


        return new UserPrinciple(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                authorities,
                roles
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
