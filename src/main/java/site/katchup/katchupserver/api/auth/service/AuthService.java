package site.katchup.katchupserver.api.auth.service;

import site.katchup.katchupserver.api.auth.dto.AuthRequestDto;
import site.katchup.katchupserver.api.auth.dto.AuthResponseDto;
import site.katchup.katchupserver.api.auth.dto.AuthTokenResponseDto;

public interface AuthService {
    AuthResponseDto socialLogin(AuthRequestDto authRequestDto);
    AuthTokenResponseDto getNewToken(String accessToken, String refreshToken);
}
