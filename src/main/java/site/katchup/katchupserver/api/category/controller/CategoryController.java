package site.katchup.katchupserver.api.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryGetResponseDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryPatchSharedStatusResponseDto;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "[Category] 카테고리 관련 API (V1)")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성 API")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 생성 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ApiResponseDto createCategoryName(Principal principal,
                                             @RequestBody @Valid final CategoryCreateRequestDto requestDto, HttpServletResponse response) {
        Long memberId = MemberUtil.getMemberId(principal);
        Long categoryId = categoryService.createCategoryName(memberId, requestDto);
        response.addHeader("Location", String.valueOf(categoryId));
        return ApiResponseDto.success();
    }

    @Operation(summary = "카테고리 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CategoryGetResponseDto>> getAllCategory(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(categoryService.getAllCategory(memberId));
    }

    @Operation(summary = "카테고리 이름 수정 API")
    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 이름 수정 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 이름 수정 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ApiResponseDto updateCategoryName(Principal principal, @PathVariable final Long categoryId,
                                             @RequestBody @Valid final CategoryUpdateRequestDto requestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        categoryService.updateCategoryName(memberId, categoryId, requestDto);
        return ApiResponseDto.success();
    }

    @Operation(summary = "카테고리 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 삭제 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponseDto.success();
    }

    @Operation(summary = "카테고리 공유 활성화 토글 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 공유 활성화 토글 변경 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 공유 활성화 토글 변경 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PatchMapping("/{categoryId}/share")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CategoryPatchSharedStatusResponseDto> toggleSharedStatus(@PathVariable Long categoryId) {
        CategoryPatchSharedStatusResponseDto categorySharedStatusDto = categoryService.toggleSharedStatus(categoryId);
        return ApiResponseDto.success(categorySharedStatusDto);
    }
}