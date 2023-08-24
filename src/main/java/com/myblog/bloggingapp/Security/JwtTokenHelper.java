package com.myblog.bloggingapp.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.*;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret = "jwtTokenKeyjwtTokenKeyjwtTokenKeyjwtTokenKeyjwtTokenKeyjwtTokenKey";
    SecretKey secretKey = generateSecretKey(secret);
     private static SecretKey generateSecretKey(String secret) {
        byte[] decodedSecret = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(decodedSecret, "HmacSHA256");
    }

    // retrieve username from jwt token

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(generateSecretKey(secret)).build().parseClaimsJws(token).getBody();
    }

    // check if token has expired

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());

    }

    // while creating the token
    // define claims of token , like issuer , expiration,subject,id
    // sign the jwt using HS512 algo and secret key
    // According to JWS compact Serialiazition (....)
    // compaction of the JWT to a URl-safe string

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(generateSecretKey(secret), SignatureAlgorithm.HS256).compact();

    }

    // validate Token

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
