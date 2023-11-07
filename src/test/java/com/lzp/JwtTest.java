package com.lzp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","zhangshan");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//添加过期时间
                .sign(Algorithm.HMAC256("lzp"));//指定算法，指定密钥
        System.out.println(token);
    }

    @Test
    public void testParse(){
        //模拟token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJleHAiOjE2OTkzMTc0NjUsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoiemhhbmdzaGFuIn19" +
                ".hM-xx0ts6O6qWJfhmfKsPz2DRZihDHgpq4g9fxkI2oA";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("lzp")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
