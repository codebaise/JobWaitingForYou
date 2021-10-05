package com.home.closematch;

import com.home.closematch.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class JWTTest {
    @Test
    void jwtLoginTest(){
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", 1);
        // 0: Seeker, 1: Hr
        claimsMap.put("userType", 2);
        String jwt = JwtUtils.createJWT(claimsMap);
        Integer userId = (Integer) JwtUtils.getExpectField(jwt, "userId");
        Integer userType = (Integer) JwtUtils.getExpectField(jwt, "userType");
        System.out.println(userType + userId);

    }
    @Test
    void buildJwt() throws InterruptedException {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("userId", 1);
        String jwt = JwtUtils.createJWT(stringObjectHashMap);
        System.out.println(jwt);
        Thread.sleep(4000);
        Claims body = Jwts.parserBuilder()
                .setSigningKey(JwtUtils.getKEY()) // <----
                .build()
                .parseClaimsJws(jwt).getBody();
        System.out.println(body.getSubject());
    }


}
