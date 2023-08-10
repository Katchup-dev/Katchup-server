package site.katchup.katchupserver.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.common.exception.UnauthorizedException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepository memberRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token.expire-length}")
    private Long accessTokenExpireLength;

    @Value("${jwt.refresh-token.expire-length}")
    private Long refreshTokenExpireLength;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_AUTHORIZATION_HEADER = "Refresh";

    public String generateAccessToken(Authentication authentication) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpireLength);

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expiration);

        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpireLength);

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expiration);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 회원 정보 추출
    public Claims getAccessTokenPayload(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Request Header에서 token 값 가져옴
    public String resolveToken(HttpServletRequest request) {

        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith("Bearer ")) {
            return "";
        } else {
            return header.split(" ")[1];
        }
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String header = request.getHeader(REFRESH_AUTHORIZATION_HEADER);

        return Objects.requireNonNullElse(header, "");
    }

    // 토큰 유효성 검증
    public JwtExceptionType validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return JwtExceptionType.VALID_JWT_TOKEN;
        } catch (io.jsonwebtoken.security.SignatureException exception) {
            return JwtExceptionType.INVALID_JWT_SIGNATURE;
        } catch (MalformedJwtException exception) {
            return JwtExceptionType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException exception) {
            return JwtExceptionType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException exception) {
            return JwtExceptionType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException exception) {
            return JwtExceptionType.EMPTY_JWT;
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long validateMemberRefreshToken(String accessToken, String refreshToken) {
        Claims claims = getAccessTokenPayload(accessToken);
        Long memberId = Long.valueOf(String.valueOf(claims.get("memberId")));
        if (!memberRepository.existsByIdAndRefreshToken(memberId, refreshToken)) {
            throw new UnauthorizedException(ErrorCode.INVALID_MEMBER);
        }
        return memberId;
    }
}
