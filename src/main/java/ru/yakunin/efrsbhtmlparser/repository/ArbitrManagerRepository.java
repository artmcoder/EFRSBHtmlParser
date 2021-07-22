package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;

import java.util.Optional;

public interface ArbitrManagerRepository extends JpaRepository<ArbitrManager, Long> {
    Optional<ArbitrManager> findByFullName (String fullName);
}
