package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Random sample text");

        // Svarer til en fabrik, der kan lave en ConnectionPool
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Dette er vores connection (forbindelse)
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        }
}