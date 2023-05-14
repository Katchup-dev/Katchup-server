package site.katchup.katchupserver.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
