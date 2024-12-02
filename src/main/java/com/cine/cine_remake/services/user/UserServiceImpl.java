package com.cine.cine_remake.services.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Role;
import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.repository.UserRepository;
import com.cine.cine_remake.security.jwt.JwtProvider;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Users saveUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setRegistrationDate(LocalDateTime.now());
        Users userCreated = userRepository.save(user);

        String jwt = jwtProvider.generateToken(userCreated);
        userCreated.setToken(jwt);
        
        return userCreated;
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Users findByUsernameReturnToken(String username){
        Users user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe: " + username));
        
        String jwt = jwtProvider.generateToken(user);
        user.setToken(jwt);
        return user;
    }
}
