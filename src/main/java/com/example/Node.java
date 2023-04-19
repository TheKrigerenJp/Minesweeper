package com.example.minesweeper3;

/**
 * Clase nodo donde se especificará los atributos y metodos que tendran los nodos
 * @author Jose_PabloGD
 * @version 16/04/2023
 */
public class Node {
    private int[] data;
    private Node next;
    private boolean Sure;
    private boolean posib;
    int J;
    int I;

    /**
     * Constructor: se crean los atributos que la clase nodo tendrá
     * @param data Un entero de listas lo que nos ayudará a encontrar posciones en la matriz
     */

    public Node(int[] data) {
        this.J = data[1];
        this.I = data[0];
        this.next = null;
        this.data = data;
        this.Sure = false;
        this.posib = false;
        System.out.println("Si mamawebo");
    }

    /**
     * Metodo que obtiene el valor de data
     * @return el valor de la variable data
     */
    public int[] getData() {
        return this.data;
    }

    /*public void setData(int[] data) {
        this.data = data;
    }*/

    /**
     * Metodo que obtiene el valor del apuntador del nodo
     * @return el valor de la variable next
     */
    public Node getNext() {
        return this.next;
    }

    /**
     * Este metodo le brinda el valor al next que se brinde como parametro
     * @param node parametro de tipo nodo para "setear" un valor al next
     */
    public void setNext(Node node) {
        this.next = node;
    }

    /**
     *
     * @return
     */
    public int get_I(){
        return this.I;
    }

    /**
     *
     * @return
     */
    public int get_J(){
        return this.J;
    }

    /**
     *
     */
    public void setSure(){
        this.Sure = true;
    }

    /**
     *
     * @return
     */
    public boolean get_Sure(){
        return this.Sure;
    }

    /**
     *
     */
    public void setPosib(){
        this.posib = true;
    }

    /**
     *
     * @return
     */
    public boolean get_Posib(){
        return this.posib;
    }

}