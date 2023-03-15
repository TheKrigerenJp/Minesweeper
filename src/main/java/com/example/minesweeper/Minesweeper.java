package com.example.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {

    static int columnas = 8;
    static int filas = 8;
    static int num_minas = 5;
    //static mina casilla[][] = new mina[][];
    static GridPane root = new GridPane();
    static Stage escenario_guard;

    public void start(Stage stage) {
        escenario_guard = stage;
        stage.setTitle("App "+ this.getClass().getSimpleName());
        //Layout que nos permita crear la matriz
        root.setHgap(1);
        root.setVgap(1);
        root.setPadding(new Insets(15,15,15,15));

        jugarPartida();

        //Se crea la escena y se asigna el escenario
        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.sizeToScene();
        stage.show();
    }
    public static void repetirPartida(Stage escenario){
        //Creamos otro layout para crear la nueva partida
        root = new GridPane();
        root.setHgap(1);
        root.setVgap(1);
        root.setPadding(new Insets(15,15,15,15));

        jugarPartida();

        //igualmente se crea un escenario y se le asigna un escenario
        Scene escena = new Scene(root);
        escenario_guard.setScene(escena);
        escenario_guard.sizeToScene();
        escenario_guard.show();

    }
    public static void jugarPartida(){
        //solicitud de datos
        //solicitud_Datos();
        //Se inserta los botones utilizando un for
        for (int i=0; i<columnas; i++){
            for (int j = 0; j<filas; j++){

            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}