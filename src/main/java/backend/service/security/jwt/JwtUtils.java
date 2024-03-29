package backend.service.security.jwt;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
//
//import com.pack.ServerSide.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import backend.service.security.services.OwnerDetailsImpl;

/** This class has 3 funtions:

 - generate a JWT from username, date, expiration, secret
 - get username from JWT
 - validate a JWT **/
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("mobappSecurityKey")
    private String jwtSecret;

    @Value("86400000")
    private int jwtExpirationMs;

    /**
     * @param userPrincipal - get info about the user in the current session
     * @return the generated JWT
     */
    public String generateJwtToken(OwnerDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    /**
     * @param username of the user in the current session
     * @return the generated JWT
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
