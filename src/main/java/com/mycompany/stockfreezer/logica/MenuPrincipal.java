package com.mycompany.stockfreezer.logica;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    
    private Controladora control = new Controladora();
    private Scanner teclado = new Scanner(System.in);

    // --- NIVEL 1: (Selección de Cajón) ---
    public void ejecutar() {
        boolean continuar = true;

        System.out.println("=== SISTEMA STOCK FREEZER v1.1 ===");
        
        while (continuar) {
            System.out.println("\n--- PANEL GENERAL ---");
            System.out.println("1. Seleccionar un Cajón para operar");
            System.out.println("2. Salir del Sistema");
            System.out.print(">> Opción: ");
            
            int opcion = leerNumero();
            
            switch (opcion) {
                case 1:
                    seleccionarCajon(); // Pantalla de selección
                    break;
                case 2:
                    System.out.println("Cerrando sistema... ¡Hasta luego!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        }
    }
    
    private void seleccionarCajon() {
        // 1. Trae la lista de todos los cajones desde la BD
        List<Cajon> lista = control.traerCajones();
        
        System.out.println("\n--- LISTA DE CAJONES DISPONIBLES ---");
        System.out.println("ID  | NOMBRE             | TAMAÑO");
        System.out.println("------------------------------------");
        
        // Muestra la tabla para que el usuario sepa qué ID elegir
        for (Cajon c : lista) {
            System.out.printf("%-3d | %-18s | %d x %d %n", 
                    c.getId(), c.getNombre(), c.getNumeroFila(), c.getNumeroColumna());
        }
        
        System.out.print("\n>> Ingrese el ID del cajón a operar (0 para volver): ");
        int idSeleccionado = leerNumero();
        
        if (idSeleccionado == 0) return;

        // Valida que el cajón exista
        Cajon cajonElegido = control.traerCajon(idSeleccionado);
        
        if (cajonElegido != null) {
            // Si existe, continúa al Nivel 2 con este cajón específico
            gestionarCajon(cajonElegido);
        } else {
            System.out.println(">>> Error: No existe un cajón con ID " + idSeleccionado);
        }
    }

    // --- NIVEL 2: OPERACIONES (Trabajar DENTRO de un cajón) ---
    private void gestionarCajon(Cajon cajonActual) {
        boolean volver = false;
        
        System.out.println("\n>>> OPERANDO EN: " + cajonActual.getNombre());

        while (!volver) {
            System.out.println("\n1. Ingresar Producto");
            System.out.println("2. Ver Mapa del Cajón");
            System.out.println("3. Retirar Producto"); 
            System.out.println("4. Volver al Panel General");
            System.out.print(">> Seleccione: ");
            
            int op = leerNumero();
            
            switch (op) {
                case 1:
                    ingresarProducto(cajonActual);
                    break;
                case 2:
                    mostrarMapa(cajonActual);
                    break;
                case 3: 
                    retirarProducto(cajonActual);
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    //    Métodos AUXILIARES
    
    private void ingresarProducto(Cajon cajon) {
        
        System.out.println("\n--- NUEVO INGRESO en " + cajon.getNombre() + " ---");
        System.out.print("Nombre del producto: ");
        String nombre = teclado.nextLine();
        
        TipoProducto tipoSeleccionado = null;
        boolean entradaValida = false;

        // Validación estricta del Enum
        while (!entradaValida) {
            System.out.println("Seleccione el Tipo de Producto:");
            int index = 1;
            for (TipoProducto t : TipoProducto.values()) {
                System.out.println(index + ". " + t);
                index++;
            }
            System.out.println("0. Cancelar");
            System.out.print("Opción: ");
            
            int opcion = leerNumero();

            if (opcion == 0) return;
            
            if (opcion >= 1 && opcion <= TipoProducto.values().length) {
                tipoSeleccionado = TipoProducto.values()[opcion - 1];
                entradaValida = true;
            } else {
                System.out.println("Opción incorrecta.");
            }
        }

        // CREACIÓN Y GUARDADO
        Producto nuevoProd = new Producto(nombre, tipoSeleccionado, 0, 0);
        
        String resultado = control.agregarProductoAutomatico(nuevoProd, cajon.getId());
        System.out.println("REPORTE: " + resultado);
    }

    private void mostrarMapa(Cajon cajon) {
        // Trae el cajón con productos desde la BD
        Cajon c = control.traerCajon(cajon.getId());

        if (c == null) {
            System.out.println("Error: No se encontró el cajón.");
            return;
        }

        System.out.println("\nEstado del Cajón: " + c.getNombre());
        List<Producto> ocupados = c.getListaProductos();

        // Doble bucle para DIBUJAR la matriz en consola
        for (int f = 0; f < c.getNumeroFila(); f++) {
            System.out.print("Fila " + f + " | ");
            
            for (int col = 0; col < c.getNumeroColumna(); col++) {
                // Guarda el objeto
                Producto encontrado = null;
                
                // ¿Hay algo en la coordenada?
                if (ocupados != null) {
                    for (Producto p : ocupados) {
                        if (p.getFila() == f && p.getColumna() == col) {
                            encontrado = p; // Encontrado: se guarda quién es
                            break;
                        }
                    }
                }

                if (encontrado != null) {
                    char inicial = encontrado.getTipo().toString().charAt(0);
                    System.out.print("[" + inicial + "]"); 
                } else {
                    System.out.print("[ ]"); 
                }
            }
            System.out.println();
        }
        //Referencias
        System.out.println("Referencias: [ ] Libre, [C]arne, [P]escado, [H]ielo, [M]asa, etc.");
    }
    
    // Helper para evitar errores si ingresan texto en vez de números
    private int leerNumero() {
        try {
            return Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void retirarProducto(Cajon cajon) {
        System.out.println("\n--- RETIRO DE MERCADERÍA en " + cajon.getNombre() + " ---");
        
        // Valida coordenadas lógicas (que no pida Fila 99)
        System.out.print("Ingrese Fila (0 a " + (cajon.getNumeroFila() - 1) + "): ");
        int fila = leerNumero();
        
        System.out.print("Ingrese Columna (0 a " + (cajon.getNumeroColumna() - 1) + "): ");
        int col = leerNumero();
        
        // Validación básica de rango
        if (fila < 0 || fila >= cajon.getNumeroFila() || col < 0 || col >= cajon.getNumeroColumna()) {
            System.out.println(">>> ERROR: Coordenadas fuera de rango del cajón.");
            return;
        }

        // Llamada a la Controladora
        String resultado = control.retirarProducto(cajon.getId(), fila, col);
        System.out.println(">>> REPORTE: " + resultado);
    }
}