package com.mycompany.stockfreezer.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Producto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
    
    // Acá se guarda el Enum como texto ("CARNE", "PESCADO", etc.)
    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;
    
    // COORDENADAS DE LA MATRIZ
    private int fila;
    private int columna;

    // RELACIÓN: Muchos Productos -> Un Cajón
    @ManyToOne 
    @JoinColumn(name = "fk_cajon") // Esto crea la columna de vínculo en la BD
    private Cajon cajon;
    
    public Producto() {
    }

    // Constructor para crear productos NUEVOS (Sin ID ni Cajón todavía)
    public Producto(String nombre, TipoProducto tipo, int fila, int columna) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }
    
    //Constructor para rescatar valores en la BD (Productos creados)
    public Producto(int id, String nombre, TipoProducto tipo, int fila, int columna, Cajon cajon) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.cajon = cajon;
    }


    // GETTERS & SETTERS
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", fila=" + fila + ", columna=" + columna + ", cajon=" + cajon + '}';
    }


    public Cajon getCajon() {
        return cajon;
    }

    public void setCajon(Cajon cajon) {
        this.cajon = cajon;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    
    
       
}
