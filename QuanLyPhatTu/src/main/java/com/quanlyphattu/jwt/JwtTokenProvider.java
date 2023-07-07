package com.quanlyphattu.jwt;

import com.quanlyphattu.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Slf4j cơ chế ghi log của lombok
@Slf4j
@Component
public class JwtTokenProvider {
    //chuỗi mã hóa và thời gian hiệu lực cấu hình trong application.properties
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private Integer JWT_EXPIRATION;

    //tạo jwt từ thông tin phật tử
    public String generateToken(CustomUserDetails customUserDetails) {
        Date ngayhienTai = new Date();
        Date thoiGianHieuLuc = new Date(ngayhienTai.getTime() + JWT_EXPIRATION);
        //Tạo chuôi jwt từ thông tin của user (cọn các thông tin duy nhất của user)
        return Jwts.builder().setSubject(customUserDetails.getEmail())
                .setIssuedAt(ngayhienTai)
                .setExpiration(thoiGianHieuLuc)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET).compact();
    }
    //Lấy lại thông tin user từ chuỗi jwt
    public String getPhatTuEmailFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        //trả lại ra email
        return claims.getSubject();
    }
    // validate thông tin chuôi jwt
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            log.error("Không tồn tại token");
        }catch (ExpiredJwtException e){
            log.error("Token hết hạn");
        }catch (UnsupportedJwtException e){
            log.error("Token không được hỗ trợ");
        }catch (IllegalArgumentException e){
            log.error("Token trống");
        }
        return false;
    }
}
