package site.katchup.katchupserver.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.member.domain.Withdraw;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
}
