package site.katchup.katchupserver.api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.card.domain.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}