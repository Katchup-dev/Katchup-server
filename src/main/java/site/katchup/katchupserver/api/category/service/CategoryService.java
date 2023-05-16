package site.katchup.katchupserver.api.category.service;

import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    //* 대분류 카테고리 전체 목록 조회
    List<CategoryResponseDto> getAllCategory(Long memberId);
}
