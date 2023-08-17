package site.katchup.katchupserver.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.common.exception.UnauthorizedException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByIdAndRefreshToken(Long id, String refreshToken);

    default Member findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_MEMBER));
    }

    default Member findByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_MEMBER));
    }
}
