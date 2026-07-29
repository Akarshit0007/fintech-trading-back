package Backend.FinTech.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    private final long EXPIRATION_TIME = 86400000;

    public String generateToken(String email) {
        return String.valueOf(Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new
                        Date(System.currentTimeMillis() + + 1000 * 60 * 60))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact());
    }
        public String extractEmail(String token) {
        return getClaims(token).getSubject();
        }
        public boolean isValidToken(String token) {
       try{
           extractEmail(token);
            return true;
       } catch(Exception e){
            return false;}
        }

        private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        }

    }

