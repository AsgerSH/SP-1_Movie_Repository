package app.daos;

import app.dtos.MovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MovieDAO implements IDAO<Movie, Integer> {

    private final EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Movie create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();

            return movie;
        }
    }

    @Override
    public List<Movie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
            return query.getResultList();
        }
    }

    @Override
    public Movie getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public Movie update(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
            return movie;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {

            Movie movie = em.find(Movie.class, id);

            if (movie != null) {
                em.getTransaction().begin();
                em.remove(movie);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            return false;
        }
    }


    // .setMaxResults fortæller databasen (gennem JPA query her) at den kun må returnere X antal rækker
    // Og eftersom vi sorterer popularitet fra højest til lavest, så returnerer den 10 højeste
    public List<Movie> topTenPopularMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    public List<Movie> topXPopularMovies(int amount) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class)
                    .setMaxResults(amount)
                    .getResultList();
        }
    }

    public List<Movie> topTenLowestRatedMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating ASC", Movie.class)
                .setMaxResults(10)
                    .getResultList();
        }
    }

    public List<Movie> topXLowestRatedMovies(int amount) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating ASC", Movie.class)
                    .setMaxResults(amount)
                    .getResultList();
        }
    }

    public List<Movie> topTenHighestRatedMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    public List<Movie> topXHighestRatedMovies(int amount) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                    .setMaxResults(amount)
                    .getResultList();
        }
    }

}
