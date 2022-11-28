package com.springboot.blog.configuration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogAppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication)
            throws InvalidKeyException, UnsupportedEncodingException {
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(getSigningKey()).compact();

        return token;
    }

    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public Boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid jwt signature");
        } catch (MalformedJwtException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid jwt");
        } catch (ExpiredJwtException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Expired jwt");
        } catch (UnsupportedJwtException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Incompatible jwt");
        } catch (IllegalArgumentException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Ilegal jwt claims");
        }
    }
}
