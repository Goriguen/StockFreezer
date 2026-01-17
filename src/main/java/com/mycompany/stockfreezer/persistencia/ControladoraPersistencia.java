package com.mycompany.stockfreezer.persistencia;

import com.mycompany.stockfreezer.logica.Cajon;
import com.mycompany.stockfreezer.logica.Producto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {
    
    ProductoJpaController prodJpa = new ProductoJpaController();
    CajonJpaController cajonJpa = new CajonJpaController();
    
    //PRODUCTO
    
    public void crearProducto(Producto prod) {
        try {
            prodJpa.create(prod);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Producto> traerProductos() {
        return prodJpa.findProductoEntities();
    }
    
    //CAJON
    
    public void crearCajon(Cajon cajon) {
        try {
            cajonJpa.create(cajon);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Cajon> traerCajones() {
        return cajonJpa.findCajonEntities();
    }

    public Cajon traerCajon(int idCajon) {
        return cajonJpa.findCajon(idCajon);
    }
    
    public Cajon traerCajonConProductos(int id) {
        return cajonJpa.findCajonConProductos(id);
    }
    
    // Método para borrar físico (DELETE)
    public void borrarProducto(int idProducto) {
        prodJpa.destroy(idProducto); // El JPA Controller ya suele tener el método destroy
    }
}