package com.ticket.ticket.service;

import com.ticket.ticket.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String getToken(UserDetails user);
    String getToken(Map<String, Object> extraClaims, UserDetails user);
    String getUsernameFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    <T> T getClaim(String token, Function<Claims, T> claimsResolver);
    Date getExpiration(String token);
    boolean isTokenExpired(String token);
}
