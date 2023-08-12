package site.katchup.katchupserver.api.auth.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.auth.dto.request.AuthRequestDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthLoginResponseDto;
import site.katchup.katchupserver.api.auth.dto.response.AuthTokenGetResponseDto;
import site.katchup.katchupserver.api.auth.dto.GoogleInfoDto;
import site.katchup.katchupserver.api.auth.service.AuthService;
import site.katchup.katchupserver.api.auth.service.GoogleAuthService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.config.jwt.JwtTokenProvider;
import site.katchup.katchupserver.config.jwt.UserAuthentication;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    @Override
    public AuthLoginResponseDto socialLogin(AuthRequestDto authRequestDto) {

        GoogleInfoDto googleInfoDto = login(authRequestDto.getAccessToken());

        String email = googleInfoDto.getEmail();
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        boolean isExistUser = isMemberByEmail(email);

        // 신규 유저 저장
        if (!isExistUser) {
            Member member = Member.builder()
                    .isNewUser(true)
                    .isDeleted(false)
                    .nickname(googleInfoDto.getNickname())
                    .email(googleInfoDto.getEmail())
                    .imageUrl(googleInfoDto.getImageUrl())
                    .refreshToken(refreshToken)
                    .build();

            memberRepository.save(member);
        }
        else memberRepository.findByEmailOrThrow(email).updateMemberStatus(false, refreshToken);

        // 등록된 유저 찾기
        Member signedMember = memberRepository.findByEmailOrThrow(email);

        Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        String nickname = signedMember.getNickname();

        return AuthLoginResponseDto.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNewUser(signedMember.isNewUser())
                .build();
    }

    @Override
    public AuthTokenGetResponseDto getNewToken(String accessToken, String refreshToken) {
        return AuthTokenGetResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private GoogleInfoDto login(String googleAccessToken) {
        return GoogleAuthService.getGoogleData(googleAccessToken);
    }
}
