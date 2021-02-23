package org.cinema.services;

import org.cinema.dao.*;
import org.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl2 implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Rabat", "Nador", "Tanger").forEach(cityName -> {
            Ville ville = new Ville();
            ville.setName(cityName);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initRoles() {
        Role role = new Role();
        role.setRole("admin");
        roleRepository.save(role);
         Role role2 = new Role();
        role2.setRole("admin");
        roleRepository.save(role2);
    }

    @Override
    public void initUsers() {
        Role role = roleRepository.findAll().get(0);
        User user = new User();
        user.setEmail("anass@gmail.com");
        user.setFirstName("anass");
        user.setLastName("saghir");
        user.setPassword(passwordEncoder.encode("123"));
        user.setUsername("admin");
        user.getRoles().addAll(roleRepository.findAll());
        userRepository.save(user);
        User user2 = new User();
        user2.setEmail("ossama@gmail.com");
        user2.setFirstName("ossama");
        user2.setLastName("hyane");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setUsername("ossamahyane");
        userRepository.save(user2);
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("MegaRama", "IMAX", "FOUNOUN", "CHAHRAZAD", "DAOULIZ").forEach(cinemaName -> {
                Cinema cinema = new Cinema();
                cinema.setName(cinemaName);
                cinema.setVille(ville);
                cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                cinemaRepository.save(cinema);

            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlaces(20 + (int) (Math.random() * 10));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlaces(); i++) {
                Place place = new Place();
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initCategories() {
        Stream.of("Histoire", "Drama", "Science-Fiction", "Horreur", "Fantaisie").forEach(s -> {
            Categorie categorie = new Categorie();
            categorie.setName(s);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1, 1.5, 2, 2.5, 3};
        List<Categorie> categorieList = categorieRepository.findAll();
        Stream.of("Cat Women", "Game Of Thrones", "Iron Man", "Le Seigneur Des Anneaux", "Spider Man", "Titanic").forEach(movieName -> {
            Film film = new Film();
            film.setTitre(movieName);
            film.setDateSortie(new Date());
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(movieName.replaceAll(" ", "") + ".jpg");
            film.setCategorie(categorieList.get(new Random().nextInt(categorieList.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[]{30, 40, 50, 60, 70, 80, 90, 100};
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    seanceRepository.findAll().forEach(seance -> {
                        Projection projection = new Projection();
                        projection.setDateProjection(new Date());
                        projection.setPrix(new Random().nextInt(prices.length));
                        projection.setFilm(filmRepository.findAll().get(new Random().nextInt(filmRepository.findAll().size())));
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        for (Projection projection : projectionRepository.findAll()) {
            for (Place place : projection.getSalle().getPlaces()) {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            }
        }
    }
}
