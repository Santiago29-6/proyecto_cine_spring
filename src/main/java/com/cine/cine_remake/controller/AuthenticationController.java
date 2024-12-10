package com.cine.cine_remake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cine_remake.model.AuthRequest;
import com.cine.cine_remake.model.AuthResponse;
import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.services.authentication.AuthenticationService;
import com.cine.cine_remake.services.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

     @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest) {
        String token = authenticationService.signInAndReturnJwt(authRequest);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
