package site.katchup.katchupserver.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.auth.dto.request.AuthRequestDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthLoginResponseDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthTokenGetResponseDto;
import site.katchup.katchupserver.api.auth.service.AuthService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "[Auth] 인증/인가 관련 API (V1)")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "소셜 로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping()
    public ApiResponseDto<AuthLoginResponseDto> socialLogin(@RequestBody AuthRequestDto authRequestDto) {

        AuthLoginResponseDto responseDto = authService.socialLogin(authRequestDto);

        // 로그인
        if (!responseDto.isNewUser()) {
            return ApiResponseDto.success(responseDto);
        }

        // 회원가입
        return ApiResponseDto.success(responseDto);
    }

    @Operation(summary = "토큰 재발급 API")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
                    @ApiResponse(responseCode = "400", description = "토큰 재발급 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<AuthTokenGetResponseDto> getNewToken(HttpServletRequest request) {
        String accessToken = (String) request.getAttribute("newAccessToken");
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        return ApiResponseDto.success(authService.getNewToken(accessToken, refreshToken));
    }
}
