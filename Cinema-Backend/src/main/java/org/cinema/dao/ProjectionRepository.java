package org.cinema.dao;

import org.cinema.entities.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface ProjectionRepository extends JpaRepository<Projection, Long> {
    @RestResource(path = "/byCinema")
    @Query("select p from Projection p where p.salle.cinema.id=:id")
    Page<Projection> getByCinema(@Param("id") Long id, Pageable pageable);

    @RestResource(path = "/byCinemaAndDate")
    @Query("select p from Projection p where p.salle.cinema.id=:id and p.dateProjection=:date")
    List<Projection> getByCinemaAndDateProjection(@Param("id") Long id, @Param("date") Date date);

    @RestResource(path = "/byDate")

    public List<Projection> getByDateProjectionAndSalleId(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("dt") Date dt, @Param("id") Long id);
}
