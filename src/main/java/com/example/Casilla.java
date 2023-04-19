package com.example.minesweeper3;

import javafx.scene.control.Button;

/**
 *
 */
public class Casilla extends Button {

    private int filas;
    private int columnas;
    private boolean esMina;
    private Tablero tablero = new Tablero(8, 8);
    public boolean mina_oculta;

    private boolean Casilla_visible;
    private int minas_Adyacentes;
    //public boolean descubrir;

    private boolean Abierta;


    /**
     * Constructor:
     * @param fila
     * @param columna
     */

    public Casilla(int fila, int columna){
        this.filas = 8;
        this.columnas = 8;
        this.esMina = false;
        mina_oculta = true;
        this.Casilla_visible = false;
        this.minas_Adyacentes = 0;
        this.setPrefSize(30,30);

    }

    /**
     *
     */

    public void colocarBomba(){

        this.esMina = true;
    }

    /**
     *
     * @return
     */

    public boolean hay_Mina() {

        return this.esMina;
    }

    /**
     *
     * @return
     */

    public int getMinas_Adyacentes(){
        return minas_Adyacentes;
    }

    /**
     *
     * @param minas_adyacentes
     */

    public void setMinas_Adyacentes(int minas_adyacentes){
        this.minas_Adyacentes = minas_adyacentes;
    }

    /**
     *
     * @return
     */

    public boolean isAbierta() {
        return this.Abierta;
    }

    /**
     *
     */

    public void abrirCasilla() {
        this.Abierta = true;
    }
}



