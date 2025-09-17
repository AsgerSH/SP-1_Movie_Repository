package app;

import app.config.HibernateConfig;
import app.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MovieService movieService = new MovieService();

        // Dette er udkommenteret - så vi ikke skal vente på alle 5000 film hver gang

//         int totalPages = 286;
//         System.out.println("Starting import of Danish movies...");
//         movieService.importDanishMovies(totalPages);
//         System.out.println("Import done");
        

    }
}