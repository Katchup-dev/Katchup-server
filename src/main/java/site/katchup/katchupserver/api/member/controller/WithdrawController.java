package site.katchup.katchupserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.member.dto.request.MemberDeleteRequestDto;
import site.katchup.katchupserver.api.member.service.WithdrawService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "[Member] 회원 탈퇴 관련 API (V1)")
public class WithdrawController {
    private final WithdrawService withdrawService;

    @Operation(summary = "회원 탈퇴 API")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "회원 탈퇴 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ApiResponseDto deleteMember(Principal principal, @RequestBody final MemberDeleteRequestDto memberDeleteRequestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        withdrawService.deleteMember(memberId, memberDeleteRequestDto);
        return ApiResponseDto.success();
    }
}
