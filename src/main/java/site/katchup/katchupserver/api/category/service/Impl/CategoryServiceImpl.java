package site.katchup.katchupserver.api.category.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

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
