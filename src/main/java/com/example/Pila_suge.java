package com.example.minesweeper3;

import java.util.LinkedList;

public class Pila_suge {
    private Node[] pila_list;
    private int maxSize = 6;
    private int top;

    public Pila_suge(){
        this.pila_list = new Node[maxSize];
        this.top =  -1;
    }

    public void push(Node object){
        if (top < maxSize){
            this.pila_list[++top] = object;
        } else{
            System.out.println("Esta llena la pila");
        }
    }

    public Node pop(){
        return this.pila_list[top--];
    }

    public Node peek(){
        return this.pila_list[top];
    }

    public int getTop(){
        return top;
    }
}
