package site.katchup.katchupserver.api.folder.service;

import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;

import java.util.List;

public interface FolderService {

    //* 중분류 폴더 전체 목록 조회
    List<FolderResponseDto> getAllFolder(Long memberId);

    //* 특정 카테고리 내 중분류 폴더 목록 조회
    List<FolderResponseDto> getByCategoryId(Long categoryId);
}
