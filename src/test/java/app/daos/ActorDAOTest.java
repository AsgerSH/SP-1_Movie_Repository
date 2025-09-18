package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Movie;
import app.util.TestDataFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActorDAOTest {

    private static EntityManagerFactory emf;
    private static ActorDAO actorDAO;
    private static MovieDAO movieDAO;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        actorDAO = new ActorDAO(emf);
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
        Actor actor = actorDAO.create(TestDataFactory.createActor("Mads Mikkelsen", movie));

        Actor found = actorDAO.getById(actor.getId());

        assertNotNull(found);
        assertEquals("Mads Mikkelsen", found.getName());
        assertEquals(movie.getId(), found.getMovie().getId());
    }

    @Test
    void getAll() {
        Movie movie = movieDAO.create(TestDataFactory.createMovie("Test Movie"));
        actorDAO.create(TestDataFactory.createActor("Mads Mikkelsen", movie));
        actorDAO.create(TestDataFactory.createActor("Nikolaj Coster-Waldau", movie));

        List<Actor> actors = actorDAO.getAll();

        assertEquals(2, actors.size());
    }


    @Test
    void update() {
        // Arrange
        Movie movie = movieDAO.create(TestDataFactory.createMovie("Movie 1"));
        Actor actor = actorDAO.create(TestDataFactory.createActor("Actor 1", movie));

        // Act
        actor.setName("Updated Actor");
        Actor updated = actorDAO.update(actor);

        // Assert
        assertNotNull(updated);
        assertEquals("Updated Actor", updated.getName());
    }

    @Test
    void delete() {
        // Arrange
        Movie movie = TestDataFactory.createMovie("Movie 2");
        Actor actor = TestDataFactory.createActor("Actor 2", movie);

        // Persist actor (movie will be cascaded and saved too)
        actorDAO.create(actor);

        // Act
        boolean deleted = actorDAO.delete(actor.getId());

        // Assert
        assertTrue(deleted, "Actor should be deleted");
        assertNull(actorDAO.getById(actor.getId()), "Deleted actor should not be found in DB");
    }


    @Test
    void getActorsFromMovie() {
        // Arrange
        Movie movie = TestDataFactory.createMovie("Movie 3");
        movieDAO.create(movie);
        Actor actor1 = TestDataFactory.createActor("Actor A", movie);
        Actor actor2 = TestDataFactory.createActor("Actor B", movie);
        actorDAO.create(actor1);
        actorDAO.create(actor2);

        // Act
        List<Actor> actors = actorDAO.getActorsFromMovie(movie);

        // Assert
        assertEquals(2, actors.size());
        assertTrue(actors.stream().anyMatch(a -> a.getName().equals("Actor A")));
        assertTrue(actors.stream().anyMatch(a -> a.getName().equals("Actor B")));
    }
}