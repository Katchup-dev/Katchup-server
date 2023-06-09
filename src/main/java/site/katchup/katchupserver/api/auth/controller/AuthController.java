package site.katchup.katchupserver.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.auth.dto.AuthRequestDto;
import site.katchup.katchupserver.api.auth.dto.AuthResponseDto;
import site.katchup.katchupserver.api.auth.dto.AuthTokenResponseDto;
import site.katchup.katchupserver.api.auth.service.AuthService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ApiResponseDto<AuthResponseDto> socialLogin(@RequestBody AuthRequestDto authRequestDto) {

        AuthResponseDto responseDto = authService.socialLogin(authRequestDto);

        // 로그인
        if (!responseDto.isNewUser()) {
            return ApiResponseDto.success(SuccessStatus.SIGNIN_SUCCESS, responseDto);
        }

        // 회원가입
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, responseDto);
    }

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<AuthTokenResponseDto> getNewToken(HttpServletRequest request) {
        String accessToken = (String) request.getAttribute("newAccessToken");
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        return ApiResponseDto.success(SuccessStatus.GET_NEW_TOKEN_SUCCESS, authService.getNewToken(accessToken, refreshToken));
    }
}
