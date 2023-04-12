package com.example.minesweeper3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Minesweeper extends Application {
    private static final int filas = 8;
    private static final int columnas = 8;
    private static final int minas = 10;
    private int minas_rest = minas;
    private int casilla_rest = filas * columnas - minas;

    private ImageView[][] imageViews;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Menú");

        Button dummy_Button = new Button("Modo Dummy");

        dummy_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane root = new GridPane();
                root.setVgap(3);
                root.setHgap(3);

                Scene dummy_scene = new Scene(root, 400, 400);


                Stage ventana_dummy = new Stage();
                ventana_dummy.setScene(dummy_scene);
                ventana_dummy.show();
                stage.close();

                llamar(root);
            }
            public void llamar(GridPane root){
                Inicializar_Juego(root);
            }


        });





        Button advance_Button = new Button("Modo Advance");
        advance_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane root = new GridPane();
                root.setVgap(3);
                root.setHgap(3);

                Scene advance_scene = new Scene(root, 400, 400);

                Stage ventana_advance = new Stage();
                ventana_advance.setScene(advance_scene);
                ventana_advance.show();
                stage.close();
            }
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(dummy_Button, advance_Button);

        //Creando escena
        Scene scene = new Scene(vBox, 400, 400);
        stage.setScene(scene);
        stage.show();


    }

    public void Inicializar_Juego(GridPane root){
        Tablero tablero_game = new Tablero(8, 8);

        tablero_game.crear_casillas();
        Random random = new Random();
        AtomicBoolean turno = new AtomicBoolean(false);
        Button[][] Matri_bot = new Button[8][8];
        turno.set(true);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Casilla botton = tablero_game.casillas[i][j];
                botton.setStyle("-fx-background-color: lightsalmon");
                botton.setOnMouseEntered(mouseEvent -> botton.setStyle("-fx-background-color: lightgray"));
                botton.setOnMouseExited(mouseEvent -> botton.setStyle("-fx-background-color: lightsalmon"));
                botton.setOnMouseClicked(mouseEvent -> botton.setStyle("-fx-background-color: lightsalmon"));
                botton.setPrefHeight(50);
                botton.setPrefWidth(50);
                Matri_bot[i][j] = botton;

                GridPane.setConstraints(botton, i, j);
                root.getChildren().addAll(botton);


            }
        }

        //Colocar bandera
        for (int fila = 0; fila<8; fila++){
            for(int columna = 0; columna<8; columna++){
                final int fila_tmp = fila;
                final int columna_tmp = columna;

                Image bandera = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\bandera.png");




                Turnos(Matri_bot, tablero_game, turno.get(), random);


            }
        }

    }

    public void Turnos(Button[][] Matri_bot, Tablero tablero_game, Boolean turno, Random random){
        if (turno == true){
            for (int i = 0; i<8; i++){
                for (int j = 0; j<8; j++){
                    int fila = i;
                    int columna = j;

                    Random rand_final = random;
                    Matri_bot[i][j].setGraphic(null);

                    int I_F = i;
                    int J_F = j;

                    Matri_bot[i][j].setOnMouseClicked((MouseEvent event) -> {
                        int FilA = GridPane.getColumnIndex(Matri_bot[fila][columna]);
                        int ColumnA = GridPane.getRowIndex(Matri_bot[fila][columna]);

                        if (event.getButton() == MouseButton.PRIMARY){
                            Matri_bot[FilA][ColumnA].setDisable(true);

                            if (tablero_game.casillas[FilA][ColumnA].hay_Mina()){

                                String mensaje;
                                Matri_bot[I_F][J_F].setGraphic(null);

                                for (int f = 0; f < 8; f++){
                                    for ( int jj = 0; jj < 8;  jj++){
                                        Matri_bot[f][jj].setDisable(true);
                                    }
                                }
                                mensaje = "PERDISTE";
                                mostrarMensaje(mensaje);
                            }else{
                                tablero_game.contarMinasAdyacentes();
                                if (tablero_game.casillas[FilA][ColumnA].getMinas_Adyacentes() != 0){
                                    tablero_game.casillas[FilA][ColumnA].setText(tablero_game.casillas[FilA][ColumnA].getMinas_Adyacentes()+"");
                                }
                                tablero_game.revelar_tablero(fila, columna);
                                Turnos(Matri_bot, tablero_game, false, rand_final);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            imageViews = new ImageView[8][8];
                            Image bandera = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\bandera.png");

                            if (imageViews[fila][columna] == null){
                                imageViews[fila][columna] = new ImageView(bandera);

                                /*imageViews.setFitHeight(40);
                                imageViews.setFitWidth(40);*/

                                ((Button) event.getSource()).setGraphic(imageViews[fila][columna]);
                            }else {
                                ((Button) event.getSource()).setGraphic(imageViews[fila][columna]);
                            }

                        }
                    });
                }
            }
        }
    }

    public void mostrarMensaje(String mensaje_final){
        Alert alerta_final = new Alert(Alert.AlertType.INFORMATION);
        alerta_final.setTitle("Buscaminas");
        alerta_final.setHeaderText(null);
        alerta_final.setContentText(mensaje_final);
        alerta_final.showAndWait();
    }



    public static void main(String[] args) {
        launch();
    }
}