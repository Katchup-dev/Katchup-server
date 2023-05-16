package site.katchup.katchupserver.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.category.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByMemberId(Long memberId);
}
