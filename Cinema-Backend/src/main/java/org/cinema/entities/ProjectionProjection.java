package org.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "p1", types = {org.cinema.entities.Projection.class})
public interface ProjectionProjection {
    public Long getId();

    public double getPrix();

    public Date getDateProjection();

    public Salle getSalle();

    public Film getFilm();

    public Seance getSeance();
}
