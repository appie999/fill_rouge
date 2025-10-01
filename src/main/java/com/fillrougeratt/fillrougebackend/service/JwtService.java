package com.fillrougeratt.fillrougebackend.service;


import com.fillrougeratt.fillrougebackend.dto.response.AuthUserResDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String secretKey = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";


    private final long expirationTime = 86400000;

    public String generateToken(AuthUserResDTO authUserResDTO){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", authUserResDTO.getId());
        claims.put("firstName", authUserResDTO.getFirstName());
        claims.put("lastName", authUserResDTO.getLastName());
        claims.put("email", authUserResDTO.getEmail());
        claims.put("role", authUserResDTO.getRole());

        return Jwts.builder()
                .claims()
                .add(claims)
                //.subject(authUserResDTO.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

  private SecretKey getSigningKey(){
        byte[] KeyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(KeyBytes);
  }
}
