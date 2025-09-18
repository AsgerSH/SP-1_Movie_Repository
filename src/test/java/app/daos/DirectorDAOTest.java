package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import app.util.TestDataFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectorDAOTest {

    private static EntityManagerFactory emf;
    private static DirectorDAO directorDAO;
    private static MovieDAO movieDAO;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        directorDAO = new DirectorDAO(emf);
        movieDAO = new MovieDAO(emf);
    }

    @BeforeEach
    void cleanDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Actor").executeUpdate();
            em.createQuery("DELETE FROM Director").executeUpdate();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void createAndGetById() {
        Movie movie = movieDAO.create(TestDataFactory.createMovie("Test Movie"));
        Director director = directorDAO.create(TestDataFactory.createDirector("Christopher Nolan", movie));

        Director found = directorDAO.getById(director.getId());

        assertNotNull(found);
        assertEquals("Christopher Nolan", found.getName());
        assertEquals(movie.getId(), found.getMovie().getId());
    }

    @Test
    void getAll() {
        Movie movie = movieDAO.create(TestDataFactory.createMovie("Test Movie"));
        directorDAO.create(TestDataFactory.createDirector("Christopher Nolan", movie));
        directorDAO.create(TestDataFactory.createDirector("James Cameron", movie));

        List<Director> directors = directorDAO.getAll();

        assertEquals(2, directors.size());
    }

    @Test
    void update() {
        // Arrange
        Movie movie = movieDAO.create(TestDataFactory.createMovie("Movie 1"));
        Director director = directorDAO.create(TestDataFactory.createDirector("Director 1", movie));

        // Act
        director.setName("Updated Director");
        Director updated = directorDAO.update(director);

        // Assert
        assertNotNull(updated);
        assertEquals("Updated Director", updated.getName());
    }

    @Test
    void delete() {
        // Arrange
        Movie movie = TestDataFactory.createMovie("Movie 2");
        Director director = TestDataFactory.createDirector("Actor 2", movie);

        // Persist actor (movie will be cascaded and saved too)
        directorDAO.create(director);

        // Act
        boolean deleted = directorDAO.delete(director.getId());

        // Assert
        assertTrue(deleted, "Director should be deleted");
        assertNull(directorDAO.getById(director.getId()), "Deleted director should not be found in DB");
    }

    @Test
    void getActorsFromMovie() {
        // Arrange
        Movie movie = TestDataFactory.createMovie("Movie 3");
        movieDAO.create(movie);
        Director director1 = TestDataFactory.createDirector("Director A", movie);
        Director director2 = TestDataFactory.createDirector("Director B", movie);
        directorDAO.create(director1);
        directorDAO.create(director2);

        // Act
        List<Director> directors = directorDAO.getDirectorsFromMovie(movie);

        // Assert
        assertEquals(2, directors.size());
        assertTrue(directors.stream().anyMatch(a -> a.getName().equals("Director A")));
        assertTrue(directors.stream().anyMatch(a -> a.getName().equals("Director B")));
    }
}