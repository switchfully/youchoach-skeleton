package com.switchfully.youcoach.security.authentication.jwt;

import com.switchfully.youcoach.security.authentication.user.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class JwtGenerator {
    private static final int TOKEN_TIME_TO_LIVE  = 3600000;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtGenerator.class);

    private String jwtSecret;

    public JwtGenerator(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateJwtToken(String id, String subject, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(subject)
                .setExpiration(new Date(new Date().getTime() + TOKEN_TIME_TO_LIVE))
                .claim("rol", authorities)
                .claim("id", id)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
            var token = request.getHeader("Authorization");
            if (!isEmpty(token) && token.startsWith("Bearer")) {
                try {
                    var parsedToken = Jwts.parser()
                            .setSigningKey(jwtSecret.getBytes())
                            .parseClaimsJws(token.replace("Bearer ", ""));

                    var username = parsedToken
                            .getBody()
                            .getSubject();


                    List<String> authoritiesInToken
                            = parsedToken.getBody().get("rol", ArrayList.class);
                    var authorities = authoritiesInToken.stream()
                            .map(Authority::valueOf)
                            .collect(Collectors.toList());

                    if (!isEmpty(username)) {
                        return new UsernamePasswordAuthenticationToken(username, null, authorities);
                    }
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
            }

            return null;
    }
}
