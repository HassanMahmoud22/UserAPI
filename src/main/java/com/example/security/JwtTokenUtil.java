package com.example.security;

import com.example.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final String SECRET = "MTIzNDU2NzgMTIzNDU2NzgMTIzNDU2NzgMTIzNDU2NzgMTIzNDU2NzgMTIzNDU2NzgMTIzNDU2NzgMTIzNDU2Nzg=";

    //retrieve username from jwt token
    private String getUserFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user);
    }

    private String doGenerateToken( Map<String, Object> claims, User user) {
        return Jwts.builder().setClaims(claims).setSubject(user.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }
    private boolean isSameUser(String token, String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map user = mapper.readValue(getUserFromToken(token), Map.class);
        return id.equals(String.valueOf(user.get("id")));
    }
    public boolean validateToken(String token, String id) {
        try{
            return !isTokenExpired(token) && isSameUser(token, id);
        }
        catch (SignatureException e){
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}