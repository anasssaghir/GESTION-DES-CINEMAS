package org.cinema;

import org.cinema.services.ICinemaInitService;
import org.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
    @Autowired
    private ICinemaInitService cinemaInitService;
    @Autowired
    RepositoryRestConfiguration restConfiguration;


    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of(Role.class,User.class, Ville.class,Categorie.class, Cinema.class, Salle.class, Projection.class,Seance.class,Film.class,Ticket.class, Place.class).forEach(aClass -> {
            restConfiguration.exposeIdsFor(aClass);
        });
        cinemaInitService.initRoles();
        cinemaInitService.initUsers();
        cinemaInitService.initVilles();
        cinemaInitService.initCinemas();
        cinemaInitService.initSalles();
        cinemaInitService.initPlaces();
        cinemaInitService.initSeances();
        cinemaInitService.initCategories();
        cinemaInitService.initFilms();
        cinemaInitService.initProjections();
        cinemaInitService.initTickets();
    }
}
