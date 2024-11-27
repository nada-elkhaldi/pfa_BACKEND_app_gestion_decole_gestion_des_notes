package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Privilege;
import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String SECRET_KEY = "482ccab91b8ef1e4956078acedfc62fd8b76b32ffcdb75b153bfd94ef1affdf9";

    public String extractUsername(String token ){
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token , UserDetails user){
        String username = extractUsername(token);
        return  (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("prenom", user.getFirstName());
        claims.put("nom", user.getLastName());
        claims.put("organisme", user.getOrganisme());
        claims.put("idUser", user.getId());
        claims.put("email", user.getEmail());
        if (user.getRole() != null) {
            Role role = user.getRole();
            claims.put("idRole", user.getRole().getId());
            claims.put("role", role.getRoleName());

            Set<String> privileges = role.getPrivileges().stream()
                    .map(Privilege::getName)
                    .collect(Collectors.toSet());
            claims.put("privileges", privileges);
        }
        //claims.put("password", user.getPassword());

        if (user.getProvince().getRegion() != null) {
            claims.put("idRegion", user.getProvince().getRegion().getId());
        }
        if (user.getProvince() != null) {
            claims.put("idProvince", user.getProvince().getId());
        }

        String token = Jwts
                .builder()
                //.subject(user.getEmail())
                .setClaims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 *60*60*1000))
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getSecretKey() {
        return this.SECRET_KEY;
    }
}
