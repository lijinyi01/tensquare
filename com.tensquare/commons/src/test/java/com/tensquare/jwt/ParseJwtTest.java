package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1ODc2MjgwMTAsImV4cCI6MTU4NzYyODA3MCwicm9sZSI6ImFkbWluIn0.RURh3Al36HoyOBcxbVIDiS3DkKZ5OagPxLx2Ro_ATh8")
                .getBody();
        System.out.println("用户ID"+claims.getId());
        System.out.println("用户名"+claims.getSubject());
        System.out.println("用户登录时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("用户过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户名"+claims.get("role"));
    }

}
