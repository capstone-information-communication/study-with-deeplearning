package core.backend.global.security;

import core.backend.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    public String createToken(Member member) {
        Date expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(String.valueOf(member.getId()))
                .setIssuer("core")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public Long validateAndGetMember(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }
}
