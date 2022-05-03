package ru.tigran.authorizationservice.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tigran.authorizationservice.database.entity.User;
import ru.tigran.authorizationservice.exception.JwtTokenMalformedException;
import ru.tigran.authorizationservice.exception.JwtTokenMissingException;

import java.util.Date;
import java.util.Random;

@Component
public class JwtUtil {
    @Autowired
    private JwtProperties properties;

    public String randomJWT(User user, JwtClaims claims) {
        long now = System.currentTimeMillis();
        long exp = now + properties.getLifeTime() * 60 * 1000;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date((now)))
                .setExpiration(new Date(exp))
                .signWith(SignatureAlgorithm.HS512, user.getSecret())
                .compact();
    }

    public Claims TryGetClaims(String token, String secret) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    private static final String alphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234567890-=+_~`/.,><?\\()*&^%$;:\"'@!";

    public static String randomSecret(){
        return randomSecret(200);
    }

    public static String randomSecret(Integer length){
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; ++i){
            Character ch = alphabet.charAt(rnd.nextInt(alphabet.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}