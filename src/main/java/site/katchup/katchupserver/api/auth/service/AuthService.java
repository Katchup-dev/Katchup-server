package site.katchup.katchupserver.api.auth.service;

import site.katchup.katchupserver.api.auth.dto.request.AuthRequestDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthLoginResponseDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthTokenGetResponseDto;

public interface AuthService {

    AuthLoginResponseDto socialLogin(AuthRequestDto authRequestDto);
    AuthTokenGetResponseDto getNewToken(String accessToken, String refreshToken);

}
