package com.mycompany.stockfreezer.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Cajon implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
    private int numeroFila; // Para vincularlo con la fila de la matriz (0, 1, 2)
    private int numeroColumna;
    
    @OneToMany(mappedBy = "cajon")
    private List<Producto> listaProductos;
            
    public Cajon() {
    }

    public Cajon(String nombre, int numeroFila, int numeroColumna) {
        this.nombre = nombre;
        this.numeroFila = numeroFila;
        this.numeroColumna = numeroColumna;
    }

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

    public int getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(int numeroFila) {
        this.numeroFila = numeroFila;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
    
    @Override
    public String toString() {
        return "Cajon{" + "id=" + id + ", nombre=" + nombre + ", numeroFila=" + numeroFila + ", numeroColumna=" + numeroColumna + '}';
    }

    
}