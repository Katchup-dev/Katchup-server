package site.katchup.katchupserver.api.keyword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.service.KeywordService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/keywords")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<KeywordResponseDto>> getAllKeyword(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.GET_ALL_KEYWORD_SUCCESS, keywordService.getAllKeyword(memberId));
    }
}
