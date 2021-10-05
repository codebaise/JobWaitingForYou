package com.home.closematch.utils;

import com.home.closematch.exception.ServiceErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // 有效期
    private static final Integer VALID_TIME = 1000 * 60 * 60 * 2;
    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String createJWT(Map<String, Object> map) {
        JwtBuilder builder = Jwts.builder()
                .setClaims(map)
                .setSubject("loginToken")
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtils.VALID_TIME))
                .signWith(JwtUtils.KEY);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    @Deprecated
    public static Object getExpectField(String jwtString, String field) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(JwtUtils.KEY) // <----
                    .build()
                    .parseClaimsJws(jwtString).getBody();
            return body.get(field);
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("token已过期");
        }
    }

    public static <T> T getExpectField(String jwtString, String field, Class<T> clazz) {
        try {
            Jws<Claims> claimsJws = verifyJwtToken(jwtString);
            Claims body = claimsJws.getBody();
//            Claims body = Jwts.parserBuilder()
//                    .setSigningKey(JwtUtil.KEY) // <----
//                    .build()
//                    .parseClaimsJws(jwtString).getBody();
            return body.get(field, clazz);
        } catch (ExpiredJwtException ex) {
            throw new ServiceErrorException(403, "token已过期");
        } catch (JwtException ex) {
            throw new ServiceErrorException(400, ex.getMessage());
        }
    }

    public static Jws<Claims> verifyJwtToken(String jwtString) {
        return Jwts.parserBuilder().setSigningKey(JwtUtils.KEY).build().parseClaimsJws(jwtString);
    }

    public static SecretKey getKEY() {
        return KEY;
    }
}
