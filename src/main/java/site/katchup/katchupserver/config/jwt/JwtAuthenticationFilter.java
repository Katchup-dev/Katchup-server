package site.katchup.katchupserver.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken != null) {
            // 토큰 검증
            if (jwtTokenProvider.validateToken(accessToken)) {
                setAuthentication(accessToken);
            } else {
                HttpServletResponse res = response;
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 토큰입니다.");
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
