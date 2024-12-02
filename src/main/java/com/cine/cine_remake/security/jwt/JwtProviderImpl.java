package com.cine.cine_remake.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cine.cine_remake.model.Users;
import com.cine.cine_remake.security.UserPrincipal;
import com.cine.cine_remake.util.SecurityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtProviderImpl implements JwtProvider{
    
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private String JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth) {
    String authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    long expirationInMs = Long.parseLong(JWT_EXPIRATION_IN_MS);

    return Jwts.builder()
            .setSubject(auth.getUsername())
            .claim("roles", authorities)
            .claim("userId", auth.getId())
            .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    @Override
    public String generateToken(Users user){
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        long expirationInMs = Long.parseLong(JWT_EXPIRATION_IN_MS);
        return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("roles", user.getRole())
        .claim("userId", user.getId())
        .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if (claims == null){
            return null;
        }
        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
            .map(SecurityUtils::convertToAuthority)
            .collect(Collectors.toSet());
        
        UserDetails userDetails = UserPrincipal.builder()
        .username(email)
        .authorities(authorities)
        .id(userId)
        .build();

        if(email == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims == null){
            return false;
        }
        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;
    }

    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if(token == null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
}
