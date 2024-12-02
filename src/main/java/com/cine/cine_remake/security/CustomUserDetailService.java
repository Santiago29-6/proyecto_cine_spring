package com.cine.cine_remake.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.services.user.UserService;
import com.cine.cine_remake.util.SecurityUtils;


@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findByUsername(username)
        .orElseThrow( () ->new UsernameNotFoundException("El usuario no fue encontrado:" + username));

        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));

        return UserPrincipal.builder()
            .user(user)
            .id(user.getId())
            .username(username)
            .password(user.getPassword())
            .authorities(authorities)
            .build();
    }
}
