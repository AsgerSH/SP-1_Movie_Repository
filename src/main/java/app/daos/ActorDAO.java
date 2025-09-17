package app.daos;

import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ActorDAO implements IDAO<Actor, Integer> {

    private final EntityManagerFactory emf;

    public ActorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Actor create(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
            return actor;
        }
    }

    @Override
    public List<Actor> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Actor> q = em.createQuery("SELECT a FROM Actor a", Actor.class);
            return q.getResultList();
        }
    }

    @Override
    public Actor getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Actor.class, id);
        }
    }

    @Override
    public Actor update(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor merged = em.merge(actor);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Actor actor = em.find(Actor.class, id);
            if (actor != null) {
                em.getTransaction().begin();
                em.remove(actor);
                em.getTransaction().commit();
                return true;
            }
            return false;
        }
    }
}
