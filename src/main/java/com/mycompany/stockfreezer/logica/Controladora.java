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
        
        // =================================================================
        // --- REGLA ANTI-CONTAMINACIÓN ---
        // =================================================================
        
        // Si hay productos, verificamos que el nuevo sea del mismo TIPO.
        if (ocupados != null && !ocupados.isEmpty()) {
            // Toma el primer producto como referencia del cajón
            TipoProducto tipoDelCajon = ocupados.get(0).getTipo();
            
            // Si son diferentes tipos -> Inválido
            if (nuevoProd.getTipo() != tipoDelCajon) {
                return "ERROR: El cajón es de " + tipoDelCajon + 
                       " y no podés mezclarlo con " + nuevoProd.getTipo();
            }
        }
        // =================================================================
        // --- FIN REGLA 
        // =================================================================
        
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
    
    public Cajon traerCajon(int id) {
        return controlPersis.traerCajonConProductos(id);
    }
    
    public ControladoraPersistencia getControlPersis() {
    return controlPersis;
    }
    
    public String retirarProducto(int idCajon, int fila, int columna) {
        // 1. Trae el cajón con sus productos actuales
        Cajon cajon = controlPersis.traerCajonConProductos(idCajon);
        
        if (cajon == null) return "Error: Cajón no encontrado.";
        
        List<Producto> lista = cajon.getListaProductos();
        Producto productoAborrar = null;
        
        // 2. Busca si hay algo en esas coordenadas
        if (lista != null) {
            for (Producto p : lista) {
                if (p.getFila() == fila && p.getColumna() == columna) {
                    productoAborrar = p;
                    break;
                }
            }
        }
        
        // 3. Ejecuta
        if (productoAborrar != null) {
            // Importante: Borra por ID único, no por coordenada
            controlPersis.borrarProducto(productoAborrar.getId());
            return "ÉXITO: Se retiró " + productoAborrar.getNombre() + " de [" + fila + "][" + columna + "]";
        } else {
            return "ERROR: No hay ningún producto en la posición [" + fila + "][" + columna + "]";
        }
    }
}