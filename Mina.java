package com.example.minesweeper2;

import javafx.scene.control.Button;

public class Mina extends Button {
    //Atribitos
    private String estado;
    private boolean esMina;
    private int contador;
    private boolean casillaVisible;

    //Constructor
    Mina(){
        setEstado("0");
        setEsMina(false);
        setContador(0);
        setCasillaVisible(false);
    }

    public String getEstado(){
        return estado;
    }
    public boolean isEsMina(){
        return esMina;
    }
    private void setEstado(String estado){
        this.estado = estado;
    }
    void setEsMina(boolean esMina){
        this.esMina = esMina;
    }
    public int getContador(){
        return contador;
    }
    Boolean setContador(int contador){
        return casillaVisible;
    }

    public boolean isCasillaVisible(){
        return casillaVisible;
    }
    public void setCasillaVisible(boolean minaVisible){
        this.casillaVisible = minaVisible;
    }
    public void cambio_estado(){
        if (getEstado()=="0"){
            setEstado("1");
        } else if (getEstado()=="1") {
            setEstado("2");
        }else{
            setEstado("");
        }
    }
}
