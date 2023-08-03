package site.katchup.katchupserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.member.dto.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public ApiResponseDto<MemberProfileGetResponseDto> getMemberProfile(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);

        MemberProfileGetResponseDto responseDto = memberService.getMemberProfile(memberId);

        return ApiResponseDto.success(SuccessStatus.GET_MEMBER_SUCCESS, responseDto);
    }
}
