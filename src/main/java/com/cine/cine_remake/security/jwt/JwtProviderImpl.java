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
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private String jwtExpirationInMs;

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_USER_ID = "userId";

    @Override
    public String generateToken(UserPrincipal auth) {
    String authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    long expirationInMs = Long.parseLong(jwtExpirationInMs);

    return Jwts.builder()
            .setSubject(auth.getUsername())
            .claim(CLAIM_ROLES, authorities)
            .claim(CLAIM_USER_ID, auth.getId())
            .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    @Override
    public String generateToken(Users user){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        long expirationInMs = Long.parseLong(jwtExpirationInMs);
        return Jwts.builder()
        .setSubject(user.getUsername())
        .claim(CLAIM_ROLES, user.getRole())
        .claim(CLAIM_USER_ID, user.getId())
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
        String username = claims.getSubject();
        Long userId = claims.get(CLAIM_USER_ID, Long.class);
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get(CLAIM_ROLES).toString().split(","))
            .map(SecurityUtils::convertToAuthority)
            .collect(Collectors.toSet());
        
        UserDetails userDetails = UserPrincipal.builder()
        .username(username)
        .authorities(authorities)
        .id(userId)
        .build();

        if(username == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);
        return claims != null && !claims.getExpiration().before(new Date());
    }

    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if(token == null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
}
