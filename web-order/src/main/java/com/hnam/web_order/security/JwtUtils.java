package com.hnam.web_order.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.*;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    //tạo khóa bí mật
    private static final String SECRET_KEY ="NguyenHoangNamDangLamProjectCaNhan19122004";

    //tgian hết hạn của token
    private static final int EXPIRATION_MS = 86400000; // 24h

    private Key getSigingKey(){
        byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Hàm tạo token từ username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRATION_MS))
                .signWith(getSigingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Hàm lấy tên đăng nhập từ token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigingKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //Hàm kiểm tra token hợp lệ
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigingKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            System.err.println("Token không hợp lệ, vui lòng thử lại.: " + e.getMessage());
        }
        return false;
    }
}
