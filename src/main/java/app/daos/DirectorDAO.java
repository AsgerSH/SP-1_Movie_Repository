package app.daos;

import app.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DirectorDAO implements IDAO<Director, Integer> {

    private final EntityManagerFactory emf;

    public DirectorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Director create(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
            return director;
        }
    }

    @Override
    public List<Director> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Director> q = em.createQuery("SELECT d FROM Director d", Director.class);
            return q.getResultList();
        }
    }

    @Override
    public Director getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Director.class, id);
        }
    }

    @Override
    public Director update(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director merged = em.merge(director);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Director director = em.find(Director.class, id);
            if (director != null) {
                em.getTransaction().begin();
                em.remove(director);
                em.getTransaction().commit();
                return true;
            }
            return false;
        }
    }
}
