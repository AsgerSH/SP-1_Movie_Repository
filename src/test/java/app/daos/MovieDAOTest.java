package app.daos;

import app.entities.Movie;
import app.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import app.config.HibernateConfig;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {

    private static EntityManagerFactory emf;
    private static MovieDAO movieDAO;
    private static MovieService movieService;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = new MovieDAO(emf);
        movieService = new MovieService();


    }

    // Deletes everything from the tables
    @BeforeEach
    void setUpEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Actor").executeUpdate();
            em.createQuery("DELETE FROM Director").executeUpdate();
            em.createQuery("DELETE FROM Movie ").executeUpdate();
            em.getTransaction().commit();

        }
            movieService.importDanishMovies(2);
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testCreateAndGetById() {
        List<Movie> movies = movieDAO.getAll();
        assertFalse(movies.isEmpty(), "There should be at least one movie in the database");

        Movie firstMovie = movies.get(0);
        Movie fetchedMovie = movieDAO.getById(firstMovie.getId());
        assertNotNull(fetchedMovie);
        assertEquals(firstMovie.getTitle(), fetchedMovie.getTitle());
    }

    @Test
    void getAll_shouldReturnAllMovies() {
        List<Movie> movies = movieDAO.getAll();
        assertEquals(40, movies.size());
    }


    @Test
    void testUpdate() {
        Movie movie = movieDAO.getAll().get(0);
        String originalTitle = movie.getTitle();
        movie.setTitle("Updated Title");

        movieDAO.update(movie);

        Movie updated = movieDAO.getById(movie.getId());
        assertEquals("Updated Title", updated.getTitle());
        assertNotEquals(originalTitle, updated.getTitle());
    }

    @Test
    void testDelete() {
        List<Movie> movies = movieDAO.getAll();
        movieDAO.delete(0);

        assertNotEquals(39, movies.size());
    }

    @Test
    void topTenPopularMovies() {
        List<Movie> topMovies = movieDAO.topTenPopularMovies();
        assertTrue(topMovies.size() <= 10);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getPopularity() >= topMovies.get(i + 1).getPopularity());
        }
    }

    @Test
    void topXPopularMovies() {
        List<Movie> topMovies = movieDAO.topXPopularMovies(5);
        assertTrue(topMovies.size() <= 5);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getPopularity() >= topMovies.get(i + 1).getPopularity());
        }
    }

    @Test
    void topTenLowestRatedMovies() {
        List<Movie> topMovies = movieDAO.topTenLowestRatedMovies();
        assertTrue(topMovies.size() <= 10);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getRating() <= topMovies.get(i + 1).getRating());
        }
    }

    @Test
    void topXLowestRatedMovies() {
        List<Movie> topMovies = movieDAO.topXLowestRatedMovies(5);
        assertTrue(topMovies.size() <= 5);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getRating() <= topMovies.get(i + 1).getRating());
        }
    }

    @Test
    void topTenHighestRatedMovies() {
        List<Movie> topMovies = movieDAO.topTenHighestRatedMovies();
        assertTrue(topMovies.size() <= 10);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getRating() >= topMovies.get(i + 1).getRating());
        }
    }

    @Test
    void testTopXHighestRatedMovies() {
        List<Movie> topMovies = movieDAO.topXHighestRatedMovies(5);
        assertTrue(topMovies.size() <= 5);
        for (int i = 0; i < topMovies.size() - 1; i++) {
            assertTrue(topMovies.get(i).getRating() >= topMovies.get(i + 1).getRating());
        }
    }

    @Test
    void testTotalAverageRating() {
        Double avgRating = movieDAO.totalAverageRating();

        assertNotNull(avgRating, "Average rating should not be null");
        assertTrue(avgRating >= 0 && avgRating <= 10, "Average rating should be between 0 and 10");

    }


    @Test
    void testFindMovieByTitle() {
        // Arrange: create and persist a test movie
        Movie testMovie = Movie.builder()
                .movieId(123)
                .title("Test Movie")
                .rating(8.5)
                .popularity(10.0)
                .description("A test movie")
                .originalLanguage("en")
                .releaseDate("2025-01-01")
                .build();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testMovie);
            em.getTransaction().commit();
        }

        // Act: find the movie by title
        Movie foundMovie = movieDAO.findMovieByTitle("Test Movie");

        // Assert
        assertNotNull(foundMovie, "Movie should be found");
        assertEquals("Test Movie", foundMovie.getTitle());
        assertEquals(123, foundMovie.getMovieId());
    }

}