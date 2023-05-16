package site.katchup.katchupserver.api.category.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static site.katchup.katchupserver.common.response.ErrorStatus.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDto> getAllCategory(Long memberId) {
        return categoryRepository.findByMemberId(memberId).stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName(), category.isShared()))
                .collect(Collectors.toList());
    }
}
