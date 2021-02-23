package org.cinema.dao;

import org.cinema.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @RestResource(path = "/byProjection")
    Page<Ticket> getByProjectionId(@Param("id") Long id, Pageable pageable);
}
