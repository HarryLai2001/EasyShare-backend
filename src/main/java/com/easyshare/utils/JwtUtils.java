package com.easyshare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtils {
    private static String key = "Lizehan";

    public String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .sign(Algorithm.HMAC256(key));
    }

    public Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
