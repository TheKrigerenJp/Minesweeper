package com.example.minesweeper3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Minesweeper extends Application {
    private static final int filas = 8;
    private static final int columnas = 8;
    private static final int minas = 10;
    //private Casilla[][] tablero = new Casilla[filas][columnas];
    private int minas_rest = minas;
    private int casilla_rest = filas * columnas - minas;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Menú");

        Button dummy_Button = new Button("Modo Dummy");
        dummy_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Aqui va juego en modo Dummy
            }
        });

        Button advance_Button = new Button("Modo Advance");
        advance_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Codigo del juego en Advance
            }
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(dummy_Button, advance_Button);

        //Creando escena
        Scene scene = new Scene(vBox, 400, 500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
