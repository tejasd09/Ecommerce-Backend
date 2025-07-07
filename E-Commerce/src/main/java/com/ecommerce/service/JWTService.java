package com.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private String secretKey="";

    public JWTService(){
        try{
            KeyGenerator generator=KeyGenerator.getInstance("HmacSHA256"); //Using HmacSha Algo to generate Key
            SecretKey secretKey1 = generator.generateKey(); //Generating key which is of type SecretKey
            secretKey = Base64.getEncoder().encodeToString(secretKey1.getEncoded());//Encoding generated key into String
        }catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String,Object> claims=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
          byte[] decode = Decoders.BASE64.decode(secretKey); //Decoding String key into byte as Key return byte
          return Keys.hmacShaKeyFor(decode);
    }


        public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();


    }
}
