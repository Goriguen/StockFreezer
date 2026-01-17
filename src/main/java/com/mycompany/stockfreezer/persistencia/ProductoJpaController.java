package com.mycompany.stockfreezer.persistencia;

import com.mycompany.stockfreezer.logica.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public class ProductoJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ProductoJpaController() {
        emf = Persistence.createEntityManagerFactory("FreezerPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            jakarta.persistence.Query q = em.createQuery("select p from Producto p");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }
    
    // Se agrega este método de forma manual
    // no estaba creado anteriormente
    public void destroy(int id) {
    EntityManager em = null;
    try {
        em = getEntityManager(); // Obtiene la conexión
        em.getTransaction().begin(); // Inicia la transacción
        
        Producto producto;
        try {
            // Intenta buscar la referencia del producto
            producto = em.getReference(Producto.class, id);
            producto.getId(); // Verifica que exista
        } catch (Exception enfe) {
            throw new RuntimeException("El producto con id " + id + " no existe.", enfe);
        }
        
        // Borra
        em.remove(producto);
        
        em.getTransaction().commit(); // Guarda los cambios
    } finally {
        if (em != null) {
            em.close(); // Cierra la conexión siempre
        }
    }
}
}