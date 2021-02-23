package org.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "p3", types = {Film.class})
public interface FilmProjection {
    public Long getId();
    public String getTitre();
    public double getDuree();
    public String getRealisateur();
    public String getDescription();
    public String getPhoto();
    public Date getDateSortie();
    public Categorie getCategorie();
}
