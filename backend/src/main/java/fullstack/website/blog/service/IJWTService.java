package fullstack.website.blog.service;

import fullstack.website.blog.entity.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJWTService {

    Account extractAccount(String token);

    Account getAccount(Claims claims);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    String generateRefreshToken(
            UserDetails userDetails
    );

    String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    );

    boolean isTokenValid(String token, UserDetails userDetails);

    Account extractAndValid(String token, String usernameOrEmail);

    String extractUsername(String token);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    Key getSignInKey();
}
