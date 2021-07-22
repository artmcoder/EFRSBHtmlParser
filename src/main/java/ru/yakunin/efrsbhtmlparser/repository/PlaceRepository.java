package ru.yakunin.efrsbhtmlparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakunin.efrsbhtmlparser.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
