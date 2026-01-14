package com.mycompany.stockfreezer.logica;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    
    private Controladora control = new Controladora();
    private Scanner teclado = new Scanner(System.in);

    public void ejecutar() {
        int opcion = 0;
        int idCajon = 1; // Uso del cajón 1 únicamentep por ahora (hardcoded)

        System.out.println("=== SISTEMA STOCK FREEZER v1.0 ===");

        while (opcion != 3) {
            System.out.println("\n--------------------------");
            System.out.println("1. Ingresar Producto (Auto)");
            System.out.println("2. Ver Mapa del Freezer");
            System.out.println("3. Salir");
            System.out.print(">> Seleccione: ");
            
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    ingresarProducto(idCajon);
                    break;
                case 2:
                    mostrarMapa(idCajon);
                    break;
                case 3:
                    System.out.println("Cerrando sistema...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        }
    }

    private void ingresarProducto(int idCajon) {
        System.out.print("Nombre del producto: ");
        String nombre = teclado.nextLine();
        
        String resultado = control.agregarProductoAutomatico(new Producto(nombre, 0, 0), idCajon);
        System.out.println("REPORTE: " + resultado);
    }

    private void mostrarMapa(int idCajon) {
        // Trae el cajón con productos desde la BD
        Cajon c = control.traerCajon(idCajon);

        if (c == null) {
            System.out.println("¡Alerta! El Cajón ID " + idCajon + " no existe en la BD.");
            System.out.println("Tip: Ejecuta el código de creación inicial o revisa la BD.");
            return;
        }

        System.out.println("\nEstado del Cajón: " + c.getNombre());
        List<Producto> ocupados = c.getListaProductos();

        // Doble bucle para DIBUJAR la matriz en consola
        for (int f = 0; f < c.getNumeroFila(); f++) {
            System.out.print("Fila " + f + " | "); // Decoración de fila
            
            for (int col = 0; col < c.getNumeroColumna(); col++) {
                boolean ocupado = false;
                
                // ¿Hay algoe en la coordenada?
                if (ocupados != null) {
                    for (Producto p : ocupados) {
                        if (p.getFila() == f && p.getColumna() == col) {
                            ocupado = true;
                            break;
                        }
                    }
                }

                if (ocupado) {
                    System.out.print("[X]"); // Ocupado
                } else {
                    System.out.print("[ ]"); // Libre
                }
            }
            System.out.println(); // Salto de línea al terminar la fila
        }
        System.out.println("Referencias: [ ] Libre, [X] Ocupado");
    }
}