package site.katchup.katchupserver.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.member.domain.Member;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByMemberId(Long memberId);
}
