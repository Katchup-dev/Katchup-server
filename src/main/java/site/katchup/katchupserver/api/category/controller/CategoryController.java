package site.katchup.katchupserver.api.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "[Category] 대분류 관련 API (V1)")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "대분류 생성 API")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "대분류 생성 성공"),
            @ApiResponse(responseCode = "400", description = "대분류 생성 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ApiResponseDto createCategoryName(Principal principal,
                                             @RequestBody @Valid final CategoryCreateRequestDto requestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        categoryService.createCategoryName(memberId, requestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_CATEGORY_NAME_SUCCESS);
    }

    @Operation(summary = "대분류 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대분류 조회 성공"),
            @ApiResponse(responseCode = "400", description = "대분류 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CategoryResponseDto>> getAllCategory(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.READ_ALL_CATEGORY_SUCCESS, categoryService.getAllCategory(memberId));
    }

    @Operation(summary = "대분류 이름 수정 API")
    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대분류 이름 수정 성공"),
            @ApiResponse(responseCode = "400", description = "대분류 이름 수정 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ApiResponseDto updateCategoryName(Principal principal, @PathVariable final Long categoryId,
                                             @RequestBody @Valid final CategoryUpdateRequestDto requestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        categoryService.updateCategoryName(memberId, categoryId, requestDto);
        return ApiResponseDto.success(SuccessStatus.UPDATE_CATEGORY_NAME_SUCCESS);
    }

    @Operation(summary = "대분류 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대분류 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "대분류 삭제 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponseDto.success(SuccessStatus.DELETE_CATEGORY_SUCCESS);
    }
}