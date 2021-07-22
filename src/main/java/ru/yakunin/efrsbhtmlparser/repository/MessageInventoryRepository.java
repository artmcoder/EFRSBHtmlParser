package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.MessageInventory;
import ru.yakunin.efrsbhtmlparser.entity.MessageValuation;

import java.util.Optional;

public interface MessageInventoryRepository extends JpaRepository<MessageInventory, Long> {
    Optional<MessageInventory> findByDebtorName (String debtorName);
    Optional<MessageInventory> findByDebtorAdres (String debtorAdres);
    Optional<MessageInventory> findBySro (String sro);
    Optional<MessageInventory> findByInventoryInfoText (String inventoryInfoText);
}
