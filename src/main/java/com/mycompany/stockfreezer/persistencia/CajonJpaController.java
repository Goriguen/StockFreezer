package com.mycompany.stockfreezer.persistencia;

import com.mycompany.stockfreezer.logica.Cajon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public class CajonJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public CajonJpaController() {
        emf = Persistence.createEntityManagerFactory("FreezerPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cajon cajon) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cajon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cajon> findCajonEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("select c from Cajon c").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Cajon findCajon(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cajon.class, id);
        } finally {
            em.close();
        }
    }
    
    public Cajon findCajonConProductos(int id) {
        EntityManager em = getEntityManager();
        try {
            // Uso de "JOIN FETCH"
            // Selecciona el Cajon (c) y adhiere (JOIN) sus productos (c.listaProductos)
            String jpql = "SELECT c FROM Cajon c LEFT JOIN FETCH c.listaProductos WHERE c.id = :id";
            
            // Consulta SQL
            jakarta.persistence.TypedQuery<Cajon> query = em.createQuery(jpql, Cajon.class);
            query.setParameter("id", id);
            
            // Ejecución
            return query.getSingleResult();
            
        } catch (Exception e) {
            // Si no encuentra nada o falla, devuelve null
            return null;
        } finally {
            em.close();
        }
    }
}