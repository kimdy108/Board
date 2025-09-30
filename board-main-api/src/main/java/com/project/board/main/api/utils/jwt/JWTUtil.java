package com.project.board.main.api.utils.jwt;

import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.utils.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JWTUtil {
    private Long expirationTime;
    private SecretKey secretKey;

    public JWTUtil(@Value("${jwt.token.secret}")String secret,
                   @Value("${jwt.token.expiration_time}")Long expirationTime) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expirationTime = expirationTime;
    }

    public String getAdminName(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("adminName", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Map<String, Object> decodeAccessToken(final String accessToken) {
        String decToken = Common.decryptStringSalt(accessToken.replace("Bearer ", ""));
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(decToken);
        }
        catch (Exception e) {

        }
        return claims.getBody();
    }

    public String createAuthToken(String userName, String userID, UUID userUUID, MemberRole role) {
        return Jwts.builder()
                .claim("userName", userName)
                .claim("userID", userID)
                .claim("userUUID", userUUID)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // 10시간
//                .expiration(new Date(System.currentTimeMillis() + 1000)) // 1초
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userID) {
        return Jwts.builder()
                .claim("userID", userID)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 24 * 7)) // 7일
                .signWith(secretKey)
                .compact();
    }
}
