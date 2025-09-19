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

        // This is all out-commented due to it not being important to run every time we run the program.
        // The next 4 lines imports all the movies, actors and directors with a cap of how many pages we set.

//         int totalPages = 100;
//         System.out.println("Starting import of Danish movies...");
//         movieService.importDanishMovies(totalPages);
//         System.out.println("Import done");


        // Top 10 lowest rated movies
        List<Movie> top10Lowest = movieDAO.topTenLowestRatedMovies();
        System.out.println("======================================");
        System.out.println("Top 10 Lowest Rated Movies");
        top10Lowest.forEach(System.out::println);

        // Top 10 best rated movies
        List<Movie> top10Highest = movieDAO.topTenHighestRatedMovies();
        System.out.println("======================================");
        System.out.println("Top 10 Highest Rated Movies");
        top10Highest.forEach(System.out::println);

        // Top 10 most popular movies
        List<Movie> top10Popular = movieDAO.topTenPopularMovies();
        System.out.println("======================================");
        System.out.println("Top 10 Popular Movies");
        top10Popular.forEach(System.out::println);

        // Top 3 of the methods above (can change our variable to any amount)
        int topAmount = 3;
        List<Movie> top50Lowest = movieDAO.topXLowestRatedMovies(topAmount);
        System.out.println("======================================");
        System.out.println("Top " + topAmount + " Lowest Rated Movies");
        top50Lowest.forEach(System.out::println);

        List<Movie> top50Highest = movieDAO.topXHighestRatedMovies(topAmount);
        System.out.println("======================================");
        System.out.println("Top " + topAmount + " Highest Rated Movies");
        top50Highest.forEach(System.out::println);

        List<Movie> top50Popular = movieDAO.topXPopularMovies(topAmount);
        System.out.println("======================================");
        System.out.println("Top " + topAmount + " Popular Movies");
        top50Popular.forEach(System.out::println);

        // Total rating of all danish movies in the latest 5 years (or of whatever in our DB)
        Double averageRating = movieDAO.totalAverageRating();
        System.out.println("======================================");
        System.out.println("Average Rating of Movies: " + averageRating);


        System.out.println("======================================");

        movieDAO.printAllActorAndDirectorsFromMovie("Buster's World");


    }
}