package com.example.minesweeper3;

import javafx.scene.control.Button;

public class Casilla extends Button {

    private int fila;
    private int columna;
    private boolean esMina;
    private boolean Casilla_visible;
    private int minas_Adyacentes;

    public Casilla(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
        this.esMina = false;
        this.Casilla_visible = false;
        this.minas_Adyacentes = 0;
        this.setPrefSize(30,30);
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }
}
