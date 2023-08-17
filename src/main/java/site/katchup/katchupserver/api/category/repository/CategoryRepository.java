package site.katchup.katchupserver.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndName(Long memberId, String name);

    default Category findByIdOrThrow(Long categoryId) {
        return findById(categoryId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_CATEGORY));
    }
}
