package site.katchup.katchupserver.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(request);
        JwtExceptionType jwtException = jwtTokenProvider.validateToken(accessToken);

        if (accessToken != null) {
            // 토큰 검증
            if (jwtException == JwtExceptionType.VALID_JWT_TOKEN) {
                setAuthentication(accessToken);
            } else {
                throw new CustomException(ErrorStatus.UNAUTHORIZED_TOKEN);
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
