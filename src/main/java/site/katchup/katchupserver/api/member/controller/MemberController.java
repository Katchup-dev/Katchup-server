package site.katchup.katchupserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.dto.MemberResponseDto;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public ApiResponseDto<MemberResponseDto> getMemberProfile(Principal principal) {
        Long memberId = Member.getMemberId(principal);

        MemberResponseDto responseDto = memberService.getMemberProfile(memberId);

        return ApiResponseDto.success(SuccessStatus.GET_MEMBER_SUCCESS, responseDto);
    }
}
