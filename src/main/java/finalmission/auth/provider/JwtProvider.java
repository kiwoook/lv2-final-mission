package finalmission.auth.provider;

import finalmission.auth.dto.MemberInfo;
import finalmission.member.domain.Member;
import finalmission.member.domain.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String CLAIM_MEMBER_ID = "memberId";
    private static final String CLAIM_ROLE = "role";

    private final Key secretKey;
    private final int jwtExpirationInMs;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") int jwtExpirationInMs) {
        this.secretKey = parseKey(secretKey);
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    private Key parseKey(String secretKey) {
        if (secretKey == null) {
            throw new IllegalArgumentException("secretKey 값은 null일 수 없습니다.");
        }

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateJwt(Member member) {
        return Jwts.builder()
                .claim(CLAIM_MEMBER_ID, member.getId())
                .claim(CLAIM_ROLE, member.getRole())
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationInMs))
                .compact();
    }

    public MemberInfo getMemberInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Double memberId = claims.get(CLAIM_MEMBER_ID, Double.class);
        Roles role = Roles.valueOf(claims.get(CLAIM_ROLE, String.class));

        return new MemberInfo(memberId.longValue(), role);
    }
}
