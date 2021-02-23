package org.cinema.dao;

import org.cinema.entities.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @RestResource(path = "/bySalle")
    Page<Place> getBySalleId(@Param("id") Long id, Pageable pageable);
}
