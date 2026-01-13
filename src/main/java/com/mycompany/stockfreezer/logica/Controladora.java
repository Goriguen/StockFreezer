package com.mycompany.stockfreezer.logica;

import com.mycompany.stockfreezer.persistencia.ControladoraPersistencia;
import java.util.List;

public class Controladora {
    
    // Conexión de la Lógica con la Persistencia
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    
    public void crearCajon(Cajon cajon) {
        controlPersis.crearCajon(cajon);
    }
    
    // Recibe el producto nuevo y el ID del cajón
    public String agregarProductoAlCajon(Producto nuevoProd, int idCajon) {
        
        
        Cajon cajon = controlPersis.traerCajonConProductos( idCajon);
        
        if (cajon == null) {
            return "Error: El cajón no existe.";
        }

        List<Producto> productosEnCajon = cajon.getListaProductos();
        
        // Recorrido de la lista buscando si está ocupada la coordenada
        if (productosEnCajon != null) {
            for (Producto p : productosEnCajon) {
                // Verifica si coinciden Fila Y Columna
                if (p.getFila() == nuevoProd.getFila() && p.getColumna() == nuevoProd.getColumna()) {
                    return "Error: La posición [" + nuevoProd.getFila() + "][" + nuevoProd.getColumna() + "] ya está ocupada por: " + p.getNombre();
                }
            }
        }
        
        // Si el lugar está libre --> Vincula y guarda
        nuevoProd.setCajon(cajon);
        controlPersis.crearProducto(nuevoProd);
        
        return "Éxito: Producto guardado correctamente.";
    }
    
    
    public List<Cajon> traerCajones() {
        return controlPersis.traerCajones();
    }
    
    public String agregarProductoAutomatico(Producto nuevoProd, int idCajon) {
        
        Cajon cajon = controlPersis.traerCajonConProductos(idCajon);
        
        if (cajon == null) return "Error: Cajón no existe";
        
        List<Producto> ocupados = cajon.getListaProductos();
        
        // Doble Bucle: Recorre Filas y Columnas
        for (int f = 0; f < cajon.getNumeroFila(); f++) {
            for (int c = 0; c < cajon.getNumeroColumna(); c++) {
                
                // Chequeo: celda libre ¿?
                boolean estaLibre = true;
                if (ocupados != null) {
                    for (Producto p : ocupados) {
                        if (p.getFila() == f && p.getColumna() == c) {
                            estaLibre = false; 
                            break; 
                        }
                    }
                }
                
                // Lugar disponible
                if (estaLibre) {
                    nuevoProd.setFila(f);
                    nuevoProd.setColumna(c);
                    nuevoProd.setCajon(cajon);
                    
                    controlPersis.crearProducto(nuevoProd);
                    return "Éxito: Ubicado en [" + f + "][" + c + "]";
                }
            }
        }
        
        return "Error: Cajón LLENO.";
    }
}