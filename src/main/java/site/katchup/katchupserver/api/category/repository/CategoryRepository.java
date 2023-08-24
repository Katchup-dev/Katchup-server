package site.katchup.katchupserver.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;


import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndIsDeletedFalse(Long id);

    boolean existsByMemberIdAndName(Long memberId, String name);

    @Query("select c from Category c where c.member.id = :memberId and c.isDeleted = false")
    List<Category> findAllByMemberIdAndNotDeleted(@Param("memberId") Long memberId);

    default Category findByIdOrThrow(Long categoryId) {
        return findByIdAndIsDeletedFalse(categoryId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_CATEGORY));
    }
}
