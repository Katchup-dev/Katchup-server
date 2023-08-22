package site.katchup.katchupserver.api.sticker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.sticker.domain.Sticker;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
}
