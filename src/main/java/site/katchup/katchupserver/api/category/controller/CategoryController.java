package site.katchup.katchupserver.api.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.config.jwt.JwtTokenProvider;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CategoryResponseDto>> getAllCategory(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        Long memberId = jwtTokenProvider.getAccessTokenPayload(accessToken);

        return ApiResponseDto.success(SuccessStatus.READ_ALL_CATEGORY_SUCCESS, categoryService.getAllCategory(memberId));
    }

}
