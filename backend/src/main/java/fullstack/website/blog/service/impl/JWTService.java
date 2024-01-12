package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.service.IJWTService;
import fullstack.website.blog.utils.enums.Role;
import fullstack.website.blog.utils.expiration.ConstantsToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static fullstack.website.blog.utils.expiration.ConstantsToken.ACCESS_TOKEN_EXPIRATION;
import static fullstack.website.blog.utils.expiration.ConstantsToken.REFRESH_TOKEN_EXPIRATION;

@Service
public class JWTService implements IJWTService {
    // Đây là token của trang web, nên nó sẽ được lưu trong file application.properties
    @Value("${jwt.secret-key}")
    private String secretKey;
    private final AccountRepository accountRepository;

    public JWTService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account extractAccount(String token) {

        Claims claims = extractAllClaims(token);
        return getAccount(claims);
    }


    @Override
    public Account getAccount(Claims claims) {
        String username = claims.get("username", String.class);
        String email = claims.get("email", String.class);
        Long accountId = claims.get("id", Long.class);
        Role role = claims.get("role", Role.class);

        return new Account(accountId, username, email, role);
    }


    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    @Override
    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Account extractAndValid(String token, String usernameOrEmail) {
        Account account = accountRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
        isTokenValid(token, account);
        return extractAccount(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}