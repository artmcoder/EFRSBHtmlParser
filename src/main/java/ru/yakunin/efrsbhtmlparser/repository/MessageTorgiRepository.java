package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageValuation;

import java.util.List;
import java.util.Optional;

public interface MessageTorgiRepository extends JpaRepository<MessageTorgi, Long> {
    List<MessageTorgi> findAllByArbitrManager(ArbitrManager arbitrManager);
    Optional<MessageTorgi> findByDebtorName (String debtorName);
    Optional<MessageTorgi> findByDebtorAdres (String debtorAdres);
    Optional<MessageTorgi> findBySro (String sro);
    Optional<MessageTorgi> findByAuctionType (String auctionType);
    Optional<MessageTorgi> findByDateTimeAuction (String dateAndTimeAuction);
    Optional<MessageTorgi> findByMarketPlace (String marketPlace);
    Optional<MessageTorgi> findByPublicationText (String publicationText);
}
