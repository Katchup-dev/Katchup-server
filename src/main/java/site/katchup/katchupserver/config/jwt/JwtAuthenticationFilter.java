package site.katchup.katchupserver.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (request.getRequestURI().equals("/api/v1/auth/token")) {
            String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

            if (jwtTokenProvider.validateToken(refreshToken) == JwtExceptionType.EMPTY_JWT || jwtTokenProvider.validateToken(accessToken) == JwtExceptionType.EMPTY_JWT) {
                jwtAuthenticationEntryPoint.setResponse(response, ErrorStatus.NO_TOKEN);
                return;
            } else if (jwtTokenProvider.validateToken(accessToken) == JwtExceptionType.EXPIRED_JWT_TOKEN) {
                if (jwtTokenProvider.validateToken(refreshToken) == JwtExceptionType.EXPIRED_JWT_TOKEN) {
                    // access, refresh 둘 다 만료
                    jwtAuthenticationEntryPoint.setResponse(response, ErrorStatus.SIGNIN_REQUIRED);
                    return;
                } else if (jwtTokenProvider.validateToken(refreshToken) == JwtExceptionType.VALID_JWT_TOKEN) {
                    // 토큰 재발급
                    Long memberId = jwtTokenProvider.validateMemberRefreshToken(accessToken, refreshToken);
                    Authentication authentication = new UserAuthentication(memberId, null, null);

                    String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

                    setAuthentication(newAccessToken);
                    request.setAttribute("newAccessToken", newAccessToken);
                }
            } else if (jwtTokenProvider.validateToken(accessToken) == JwtExceptionType.VALID_JWT_TOKEN) {
                jwtAuthenticationEntryPoint.setResponse(response, ErrorStatus.VALID_ACCESS_TOKEN);
                return;
            } else {
                throw new CustomException(ErrorStatus.UNAUTHORIZED_TOKEN);
            }
        }
        else {
            JwtExceptionType jwtException = jwtTokenProvider.validateToken(accessToken);

            if (accessToken != null) {
                // 토큰 검증
                if (jwtException == JwtExceptionType.VALID_JWT_TOKEN) {
                    setAuthentication(accessToken);
                } else {
                    throw new CustomException(ErrorStatus.UNAUTHORIZED_TOKEN);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void setAuthentication(String jwtToken) {
        Long userId = jwtTokenProvider.getAccessTokenPayload(jwtToken);
        Authentication authentication = new UserAuthentication(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
