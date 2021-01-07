package com.switchfully.youcoach.security.authentication.jwt;

import com.switchfully.youcoach.security.authentication.user.Authority;
import com.switchfully.youcoach.security.authentication.user.api.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class JwtGenerator {
    private static final int TOKEN_TIME_TO_LIVE = 3600000;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtGenerator.class);

    private final String jwtSecret;

    public JwtGenerator(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public UsernamePasswordAuthenticationToken convertToken(String token) {
        if (isEmpty(token)) {
            return null;
        }

        Jws<Claims> parsedToken = null;
        try {
            parsedToken = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
        } catch (ExpiredJwtException exception) {
            LOGGER.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            LOGGER.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            LOGGER.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            LOGGER.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            LOGGER.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }

        if(parsedToken == null){
            return null;
        }

        var username = parsedToken.getBody().getSubject();

        List<String> authoritiesInToken = parsedToken.getBody().get("rol", ArrayList.class);
        var authorities = authoritiesInToken.stream()
                .map(Authority::valueOf)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public String generateToken(Account account) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(account.getEmail())
                .setExpiration(new Date(new Date().getTime() + TOKEN_TIME_TO_LIVE))
                .claim("rol", account.getAuthorities())
                .claim("id", account.getId())
                .compact();
    }
}
