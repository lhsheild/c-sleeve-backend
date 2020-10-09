package com.sheildog.csleevebackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author a7818
 */
@Component
public class JWTToken {

    private static String jwtKey;

    @Value("${c-sleeve-backend.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JWTToken.jwtKey = jwtKey;
    }

    private static Integer expireTime;

    @Value("${c-sleeve-backend.security.token-expired-in}")
    public void setExpireTime(Integer expireTime) {
        JWTToken.expireTime = expireTime;
    }

    private static Integer defaultScope = 8;

    public static String makeToken(Long uid){
        return JWTToken.getToken(uid, JWTToken.defaultScope);
    }

    public static String makeToken(Long uid, Integer scope) {
        return JWTToken.getToken(uid, scope);
    }

    private static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JWTToken.jwtKey);
        Map<String, Date> map = JWTToken.calculateExpiredIssues();

        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
    }

    private static Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JWTToken.expireTime);
        map.put("now", now);
        map.put("expiredTime", calendar.getTime());
        return map;
    }
}
