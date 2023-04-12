package com.example.minesweeper3;

import java.util.Random;



public class Tablero {
    public Casilla[][] casillas;

    //Tablero tablero = new Tablero(8, 8, 10);

    private int numMinas = 8;
    private int numCasilla_tapada;
    private int numCasilla_faltantes;
    private boolean game_over;
    private boolean victoria;

    private int filas = 8;
    private int columnas = 8;

    public Tablero(int numFilas, int numColumnas){
        this.casillas = new Casilla[numFilas][numColumnas];
        this.numCasilla_tapada = numFilas * numColumnas;
        this.numCasilla_faltantes = numFilas * numColumnas - numMinas;
        this.game_over = false;
        this.victoria = false;

    }

    public int getFilas(){
        return filas;
    }

    public int getColummnas(){
        return columnas;
    }

    public Casilla getCasilla(int fila, int columna){
        return casillas[fila][columna];
    }

    public void crear_casillas(){

        //creamos las casillas
        for(int fila = 0; fila < casillas.length; fila++){
            for(int columna = 0; columna < this.casillas[fila].length; columna++){

                casillas[fila][columna] = new Casilla(fila, columna);
            }
        }

        colocarMinas();
    }



    private void colocarMinas(){
        int minas_colo = 0;
        Random random = new Random();

        while (minas_colo != numMinas){

            int fila = (int)(Math.random()*casillas.length);
            int columna = (int)(Math.random()*casillas[0].length);

            if (!this.casillas[fila][columna].hay_Mina()){
                casillas[fila][columna].colocarBomba();
                minas_colo++;
            }else {
                minas_colo = minas_colo;
            }

        }

        //Se actualiza minas adyacentes
        /*for (int fila = 0; fila < filas; fila++){
            for (int columna = 0; columna < columnas; columna++){
                if (tablero[fila][columna].esMina){
                    for (Casilla vecinito : getVecinos(tablero[fila][columna])){
                        vecinito.aumenta_minasCercanas();
                    }
                }
            }
        }*/
    }

    public int getNumeroMinasCercanas (Casilla casilla){

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
    }

    /*private void descubrirCasilla(Casilla casilla) {
        casilla.setMina_oculta(false);
        if (!casilla.mina_oculta) {
            return;
        }

        casilla.descubrir();
        numCasilla_faltantes--;

        if (casilla.hay_Mina()) {
            minas_rest--;
        }

        if (casilla.getNumeroMinasCercanas == 0) {
            for (Casilla vecino : getVecinos(casilla)) {
                descubrirCasilla(vecino);
            }
        }
    }*/


    public void revelar_tablero(int fila, int columna){
        if (fila<0 || columna<0 || fila >= getFilas() || columna >= getColummnas()){
            return;
        }
        if (casillas[fila][columna].isAbierta()){
            return;
        }

        if (casillas[fila][columna].getMinas_Adyacentes() != 0){
            casillas[fila][columna].setText(casillas[fila][columna].getMinas_Adyacentes()+"");
            casillas[fila][columna].abrirCasilla();
            casillas[fila][columna].setDisable(true);
            return;
        }

        casillas[fila][columna].abrirCasilla();
        casillas[fila][columna].setDisable(true);

        for (int i = fila-1; i<=fila+1; i++){
            for(int j = columna-1; j<=columna+1; j++){
                revelar_tablero(i, j);
            }
        }





    }



    public boolean pos(int i ,int j){
        if(i>=0&&j>=0&&i<=7&&j<=7){
            return true;
        }else {
            return false;
        }
    }


    /**
     * Se generan los numeros de minas
     * adyacentes y se agregan a la variable
     * numrev en la matriz de valores. Esto
     * se hace para todos los espacios en la matriz.
     */
    public void contarMinasAdyacentes() { // genera los numeros de minas adyacentes y los añade a matrizvalores.numrev
        for (int i=0; i<=7; i++) {
            for (int j=0; j<=7; j++) { // indices para recorrer la matriz
                int contador = 0;
                if (!casillas[i][j].hay_Mina()) { // si no hay una mina en el espacio
                    if (pos(i - 1, j - 1)) { // si el espacio de arriba a la izquierda no se sale del arreglo
                        if (casillas[i - 1][j - 1].hay_Mina()) { // si el espacio de arriba a la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i - 1, j)) { // si el espacio de arriba no se sale del arreglo
                        if (casillas[i - 1][j].hay_Mina()) { // si el espacio de arriba es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i - 1, j + 1)) { // si el espacio de arriba a la derecha no se sale del arreglo
                        if (casillas[i - 1][j + 1].hay_Mina()) { // si el espacio de arriba a la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i, j - 1)) { // si el espacio de la izquierda no se sale del arreglo
                        if (casillas[i][j - 1].hay_Mina()) { // si el espacio de la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i, j + 1)) { // si el espacio de la derecha no se sale del arreglo
                        if (casillas[i][j + 1].hay_Mina()) { // si el espacio de la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i + 1, j - 1)) { // si el espacio de abajo a la izquierda no se sale del arreglo
                        if (casillas[i + 1][j - 1].hay_Mina()) { // si el espacio de abajo a la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i + 1, j)) { // si el espacio de abajo no se sale del arreglo
                        if (casillas[i + 1][j].hay_Mina()) { // si el espacio de abajo es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i + 1, j + 1)) { // si el espacio de abajo a la derecha no se sale del arreglo
                        if (casillas[i + 1][j + 1].hay_Mina()) { // si el espacio de abajo a la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    casillas[i][j].setMinas_Adyacentes(contador); // se asigna el contador a la variable numrev en la matriz de valores
                } else { // si el espacio es una mina
                    casillas[i][j].setMinas_Adyacentes(-1); // se le asigna un -1 para representar que hay una mina
                }
            }    }
    }



}
