package site.katchup.katchupserver.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        setResponse(response, HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_TOKEN);
    }


    public void setResponse(HttpServletResponse response, HttpStatus statusCode, ErrorCode status) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponseDto apiResponse = ApiResponseDto.error(status.getCode());
        response.getWriter().println(mapper.writeValueAsString(apiResponse));
    }
}
