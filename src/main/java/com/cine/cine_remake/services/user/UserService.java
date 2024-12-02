package com.cine.cine_remake.services.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Users;


@Service
public interface UserService {

    Users saveUser(Users user);

    Optional<Users> findByUsername(String username);

    Users findByUsernameReturnToken(String username);

}
