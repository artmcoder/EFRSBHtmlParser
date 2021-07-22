package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.MessageProcedure;

import java.util.Optional;

public interface MessageProcedureRepository extends JpaRepository<MessageProcedure, Long> {
    Optional<MessageProcedure> findByDebtorName (String debtorName);
    Optional<MessageProcedure> findByDebtorAdres (String debtorAdres);
    Optional<MessageProcedure> findBySro (String sro);
    Optional<MessageProcedure> findByInn (String inn);
}
