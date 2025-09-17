package app;

import app.config.HibernateConfig;
import app.daos.MovieDAO;
import app.entities.Movie;
import app.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        MovieService movieService = new MovieService();
        MovieDAO movieDAO = new MovieDAO(emf);

        // Top 10 værste film
        List<Movie> top10Lowest = movieDAO.topTenLowestRatedMovies();
        System.out.println("Top 10 Lowest Rated Movies");
        top10Lowest.forEach(System.out::println);

        // Top 10 bedst anmeldte film
        List<Movie> top10Highest = movieDAO.topTenHighestRatedMovies();
        System.out.println("Top 10 Highest Rated Movies");
        top10Highest.forEach(System.out::println);
      
        // Top 10 mest populære film
        List<Movie> top10Popular = movieDAO.topTenPopularMovies();
        System.out.println("Top 10 Popular Movies");
        top10Popular.forEach(System.out::println);

        // Top 50 af de ovennævnte metoder
        List<Movie> top50Lowest = movieDAO.topXLowestRatedMovies(50);
        System.out.println("Top 50 Lowest Rated Movies");
        top50Lowest.forEach(System.out::println);

        List<Movie> top50Highest = movieDAO.topXHighestRatedMovies(50);
        System.out.println("Top 50 Highest Rated Movies");
        top50Highest.forEach(System.out::println);

        List<Movie> top50Popular = movieDAO.topXPopularMovies(50);
        System.out.println("Top 50 Popular Movies");
        top50Popular.forEach(System.out::println);

        // Dette er udkommenteret - så vi ikke skal vente på alle 5000 film hver gang

//         int totalPages = 286;
//         System.out.println("Starting import of Danish movies...");
//         movieService.importDanishMovies(totalPages);
//         System.out.println("Import done");
        

    }
}