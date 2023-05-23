package site.katchup.katchupserver.api.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CategoryResponseDto>> getAllCategory(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.READ_ALL_CATEGORY_SUCCESS, categoryService.getAllCategory(memberId));
    }

    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateCategoryName(Principal principal, @PathVariable final Long categoryId,
                                             @RequestBody @Valid final CategoryUpdateRequestDto requestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        categoryService.updateCategoryName(memberId, categoryId, requestDto);
        return ApiResponseDto.success(SuccessStatus.UPDATE_CATEGORY_NAME_SUCCESS);
    }
}
