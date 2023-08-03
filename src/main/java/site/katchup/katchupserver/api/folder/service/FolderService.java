package site.katchup.katchupserver.api.folder.service;

import site.katchup.katchupserver.api.folder.dto.request.FolderCreateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderGetResponseDto;

import java.util.List;

public interface FolderService {

    //* 중분류 폴더 전체 목록 조회
    List<FolderGetResponseDto> getAllFolder(Long memberId);

    //* 특정 카테고리 내 중분류 폴더 목록 조회
    List<FolderGetResponseDto> getByCategoryId(Long categoryId);

    //* 중분류명 수정
    void updateFolderName(Long folderId, FolderUpdateRequestDto folderUpdateRequestDto);

    void createFolderName(FolderCreateRequestDto folderCreateRequestDto);
    void deleteFolder(Long folderId);
}
