package com.icritic.gateway.filters.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtProvider {

    @Value("${application.properties.jwtSecret}")
    private String SECRET;

    public boolean validateToken(String token) {
        JwtParser jwtParser = createTokenParser();

        try {
            jwtParser.parseClaimsJws(token);

            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private JwtParser createTokenParser() {
        return Jwts.parser().setSigningKey(SECRET);
    }

    public String decryptToken(String token) {
        return Jwts.builder()
                .setId(getTokenId(token))
                .claim("userId", getUserIdFromToken(token))
                .claim("role", getUserRoleFromToken(token))
                .compact();
    }

    public String getTokenId(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getId();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get("userId").toString();
    }

    public String getUserRoleFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get("role").toString();
    }
}
