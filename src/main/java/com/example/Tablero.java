package com.example.minesweeper3;

import java.util.Random;


/**
 *
 */
public class Tablero {
    public Casilla[][] casillas;

    //Tablero tablero = new Tablero(8, 8, 10);

    private int numMinas = 8;
    //private int numCasilla_tapada;
    //private int numCasilla_faltantes;
    //private boolean game_over;
    //private boolean victoria;

    private int filas = 8;
    private int columnas = 8;

    /**
     *
     * @param numFilas
     * @param numColumnas
     */
    public Tablero(int numFilas, int numColumnas){
        this.casillas = new Casilla[numFilas][numColumnas];
        //this.numCasilla_tapada = numFilas * numColumnas;
        //this.numCasilla_faltantes = numFilas * numColumnas - numMinas;
        //this.game_over = false;
        //this.victoria = false;

    }

    /**
     *
     * @return
     */
    public int getFilas(){
        return filas;
    }

    /**
     *
     * @return
     */
    public int getColummnas(){
        return columnas;
    }

    /**
     *
     * @param fila
     * @param columna
     * @return
     */
    public Casilla getCasilla(int fila, int columna){
        return casillas[fila][columna];
    }

    /**
     * Se crea las casillas y se llama al metodo que coloca minas
     *
     */
    public void crear_casillas(){

        //creamos las casillas
        for(int fila = 0; fila < casillas.length; fila++){
            for(int columna = 0; columna < this.casillas[fila].length; columna++){

                casillas[fila][columna] = new Casilla(fila, columna);
            }
        }

        colocarMinas();
    }

    /**
     * Se coloca de manera aleatoria dentro de los limites del gridpane la cantidad de minas
     *
     */

    private void colocarMinas(){
        int minas_colo = 0;
        Random random = new Random();

        while (minas_colo != numMinas){

            int fila = (int)(Math.random()*casillas.length);
            int columna = (int)(Math.random()*casillas[0].length);
            System.out.println(columna+"" +","+ fila+"");

            if (!this.casillas[fila][columna].hay_Mina()){

                casillas[fila][columna].colocarBomba();
                minas_colo++;
            }else {
                minas_colo = minas_colo;
            }

        }

    }

    /*public int getNumeroMinasCercanas (Casilla casilla){

        int fila = casilla.getFila();
        int columna = casilla.getColumna();
        int num_minas = 0;

        for (int i = fila - 1; i <= fila + 1; i++){
            for (int j = columna - 1; j <= columna + 1; j++){
                if (i >= 0 && i < filas && j >= 0 && j < columnas){
                    Casilla vecino = casillas[i][j];
                    if (vecino.hay_Mina()){
                        num_minas++;
                    }
                }
            }
        }
        return num_minas;
    }*/

    /**
     * Este metodo es el que genera el efecto "cascada" al encontrar varias casillas seguidas sin minas alrededor, descubrirá esas casillas
     * @param fila
     * @param columna
     */

    public void revelar_tablero(int fila, int columna){
        System.out.println("Entra en revelar");
        if (fila<0 || columna<0 || fila >= getFilas() || columna >= getColummnas()){
            return;
        }
        if (casillas[fila][columna].isAbierta()){
            return;
        }

        if (casillas[fila][columna].getMinas_Adyacentes() != 0){
            casillas[fila][columna].setText(casillas[fila][columna].getMinas_Adyacentes()+"");
            casillas[fila][columna].setStyle("-fx-background-color: red");
            casillas[fila][columna].abrirCasilla();
            casillas[fila][columna].setDisable(true);
            casillas[fila][columna].setStyle("-fx-background-color: darkred");
            return;
        }

        casillas[fila][columna].abrirCasilla();
        casillas[fila][columna].setDisable(true);
        casillas[fila][columna].setStyle("-fx-background-color: red");

        for (int i = fila-1; i<=fila+1; i++){
            for(int j = columna-1; j<=columna+1; j++){
                revelar_tablero(i, j);
            }
        }





    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public boolean posi(int i ,int j){
        if(i>=0&&j>=0&&i<=7&&j<=7){
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     */
    public void contarMinasAdyacentes() { 
        for (int i=0; i<=7; i++) {
            for (int j=0; j<=7; j++) { 
                int contador = 0;
                if (!casillas[i][j].hay_Mina()) { 
                    if (posi(i - 1, j - 1)) { 
                        if (casillas[i - 1][j - 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i - 1, j)) { 
                        if (casillas[i - 1][j].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i - 1, j + 1)) { 
                        if (casillas[i - 1][j + 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i, j - 1)) { 
                        if (casillas[i][j - 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i, j + 1)) { 
                        if (casillas[i][j + 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i + 1, j - 1)) { 
                        if (casillas[i + 1][j - 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i + 1, j)) { 
                        if (casillas[i + 1][j].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    if (posi(i + 1, j + 1)) { 
                        if (casillas[i + 1][j + 1].hay_Mina()) { 
                            contador++; 
                        }
                    }
                    casillas[i][j].setMinas_Adyacentes(contador); 
                } else { 
                    casillas[i][j].setMinas_Adyacentes(-1); 
                }
            }    }
    }



}

