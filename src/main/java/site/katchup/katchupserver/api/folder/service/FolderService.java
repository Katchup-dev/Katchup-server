package site.katchup.katchupserver.api.folder.service;

import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderCreateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;

import java.util.List;

public interface FolderService {

    //* 중분류 폴더 전체 목록 조회
    List<FolderResponseDto> getAllFolder(Long memberId);

    //* 특정 카테고리 내 중분류 폴더 목록 조회
    List<FolderResponseDto> getByCategoryId(Long categoryId);

    //* 중분류명 수정
    void updateFolderName(Long folderId, FolderUpdateRequestDto folderUpdateRequestDto);

    void createFolderName(FolderCreateRequestDto folderCreateRequestDto);
}
