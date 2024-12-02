package com.cine.cine_remake.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.AuthRequest;
import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.security.UserPrincipal;
import com.cine.cine_remake.security.jwt.JwtProvider;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public String signInAndReturnJwt(AuthRequest signInRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        Users singInUser = userPrincipal.getUser();
        singInUser.setToken(jwt);

        return jwt;
    } 
}
