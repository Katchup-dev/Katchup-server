package site.katchup.katchupserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.member.dto.request.MemberProfileUpdateRequestDto;
import site.katchup.katchupserver.api.member.dto.response.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "[Member] 사용자 관련 API (V1)")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회 API")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    @GetMapping("/profile")
    public ApiResponseDto<MemberProfileGetResponseDto> getMemberProfile(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);

        MemberProfileGetResponseDto responseDto = memberService.getMemberProfile(memberId);

        return ApiResponseDto.success(responseDto);
    }

    @Operation(summary = "회원 프로필 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 프로필 수정 성공"),
            @ApiResponse(responseCode = "400", description = "회원 프로필 수정 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PutMapping("/profile/me")
    public ApiResponseDto updateMemberProfile(@RequestPart("profileImage") @Nullable MultipartFile profileImage, @Valid MemberProfileUpdateRequestDto request, Principal principal) {
        memberService.updateMemberProfile(MemberUtil.getMemberId(principal), profileImage, request);
        return ApiResponseDto.success();
    }

}
