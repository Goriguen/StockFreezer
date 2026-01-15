package com.mycompany.stockfreezer;
import com.mycompany.stockfreezer.logica.CargaDeDatos;
import com.mycompany.stockfreezer.logica.Controladora;
import com.mycompany.stockfreezer.logica.MenuPrincipal;

public class StockFreezer {

    public static void main(String[] args) {
        
        Controladora control = new Controladora();
           
        CargaDeDatos carga = new CargaDeDatos(control.getControlPersis());
        carga.inicializarDatos();
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.ejecutar(); 
    }
    
}