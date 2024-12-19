package com.cine.cine_remake.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.model.enums.Role;
import com.cine.cine_remake.security.UserPrincipal;
import com.cine.cine_remake.services.user.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("me")
    public ResponseEntity<Users> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(
            userService.findByUsernameReturnToken(userPrincipal.getUsername()), 
            HttpStatus.OK
        );
    }

    @GetMapping("admin/all/{role}")
    public ResponseEntity<List<Users>> getAllUsers(@PathVariable("role") Role role){
        return ResponseEntity.ok(userService.findUserByRole(role));
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(!(userService.findUserById(id).isPresent()));
    }

    @PostMapping("admin/create")
    public ResponseEntity<Users> saveUser(@RequestBody Users user){
        if (user.getId() != null) {
            Optional<Users> existingUser = userService.findUserById(user.getId());
            if (existingUser.isPresent()) {
                userService.saveUser(user);
                return ResponseEntity.ok(user);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

  
        
}