package com.mycompany.stockfreezer.logica;
import com.mycompany.stockfreezer.persistencia.ControladoraPersistencia;
import java.util.List;

public class CargaDeDatos {
    
    private ControladoraPersistencia controlPersis;

    public CargaDeDatos(ControladoraPersistencia controlPersis) {
        this.controlPersis = controlPersis;
    }

    public void inicializarDatos() {
        // Verifica si ya existen cajones, para no duplicar
        List<Cajon> cajonesExistentes = controlPersis.traerCajones();

        if (cajonesExistentes != null && !cajonesExistentes.isEmpty()) {
            return; // Ya existen datos, no hace nada
        }

        System.out.println(">>> INICIALIZANDO ARQUITECTURA DEL FREEZER (12 Compartimientos)<<<");

        // Contadores globales para los nombres (IDB-1, IDB-2... IDS-1, IDS-2...)
        int contadorBig = 1;
        int contadorSmall = 1;

        // Bucle para crear los 3 Niveles (Pisos)
        // i=1 (Inferior), i=2 (Medio), i=3 (Superior)
        for (int nivel = 1; nivel <= 3; nivel++) {
            
            String nombreNivel = (nivel == 1) ? "Inferior" : (nivel == 2) ? "Medio" : "Superior";
            System.out.println("--- Creando Nivel " + nombreNivel + " ---");

            // --- FASE 1: Los 2 cajones grandes Grandes (Izquierda) ---
            for (int b = 1; b <= 2; b++) {
                Cajon c = new Cajon();
                // Nombres: IDB-1, IDB-2, etc.
                c.setNombre("IDB-" + contadorBig + " (" + nombreNivel + ")"); 
                c.setNumeroFila(3);
                c.setNumeroColumna(4); // 3x4
                
                controlPersis.crearCajon(c);
                System.out.println("   + Creado: " + c.getNombre() + " [3x4]");
                contadorBig++;
            }

            // --- FASE 2: Los 2 cajones chicos (Derecha) ---
            for (int s = 1; s <= 2; s++) {
                Cajon c = new Cajon();
                // Nombres: IDS-1, IDS-2, etc. 
                c.setNombre("IDS-" + contadorSmall + " (" + nombreNivel + ")");
                c.setNumeroFila(3);
                c.setNumeroColumna(2); // 3x2
                
                controlPersis.crearCajon(c);
                System.out.println("   + Creado: " + c.getNombre() + " [3x2]");
                contadorSmall++;
            }
        }
        
        System.out.println(">>> ARQUITECTURA DE 9x12 COMPLETADA.");
    }
    
}
