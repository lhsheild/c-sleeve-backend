package com.sheildog.csleevebackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author a7818
 */
@Component
public class JwtToken {

    private static String jwtKey;

    @Value("${c-sleeve-backend.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    private static Integer expireTime;

    @Value("${c-sleeve-backend.security.token-expired-in}")
    public void setExpireTime(Integer expireTime) {
        JwtToken.expireTime = expireTime;
    }

    private static final Integer defaultScope = 8;

    public static Optional<Map<String, Claim>> getClaims(String token) {
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(token);
            return Optional.of(decodedJWT.getClaims());
        }
        catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, JwtToken.defaultScope);
    }

    public static String makeToken(Long uid, Integer scope) {
        return JwtToken.getToken(uid, scope);
    }

    private static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String, Date> map = JwtToken.calculateExpiredIssues();

        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
    }

    public static Boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e){
            return false;
        }
        return true;
    }

    private static Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expireTime);
        map.put("now", now);
        map.put("expiredTime", calendar.getTime());
        return map;
    }
}
