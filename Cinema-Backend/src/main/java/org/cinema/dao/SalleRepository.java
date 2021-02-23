package org.cinema.dao;

import org.cinema.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface SalleRepository extends JpaRepository<Salle, Long> {
    @RestResource(path = "/byCinema")
    Page<Salle> getSalleByCinemaId(@Param("id") Long id, Pageable pageable);
    List<Salle> getSalleByCinemaId(@Param("id") Long id);
}
