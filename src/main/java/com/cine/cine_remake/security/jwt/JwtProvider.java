package com.cine.cine_remake.security.jwt;

import org.springframework.security.core.Authentication;

import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.security.UserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtProvider {

    String generateToken(UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
    
    String generateToken(Users user);

}
