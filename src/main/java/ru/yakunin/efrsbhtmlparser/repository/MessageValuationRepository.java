package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.MessageValuation;

import java.util.Optional;

public interface MessageValuationRepository extends JpaRepository<MessageValuation, Long> {
    Optional<MessageValuation> findByDebtorName (String debtorName);
    Optional<MessageValuation> findByDebtorAdres (String debtorAdres);
    Optional<MessageValuation> findBySro (String sro);
    Optional<MessageValuation> findByValuationInfoText (String valuationInfoText);
}
