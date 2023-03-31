package com.example.minesweeper2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Minesweeper2 extends Application {

    static int columnas = 8;
    static int filas = 8;
    static int num_minas = 5;
    static Mina casilla[][] = new Mina[columnas][filas];
    static GridPane root = new GridPane();
    static Stage escenario_guard;

    public void start(Stage stage) {
        escenario_guard = stage;
        stage.setTitle("App "+ this.getClass().getSimpleName());
        //Layout que nos permita crear la matriz
        root.setHgap(2);
        root.setVgap(2);
        root.setPadding(new Insets(15,15,15,15));

        jugarPartida();

        //Se crea la escena y se asigna el escenario
        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.sizeToScene();
        stage.show();
    }
    public static void repetirPartida(Stage escenario){
        //Creamos otro oot = new GridPane();layout para crear la nueva partida
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
        datos_usuario();
        //Se inserta los botones utilizando un for
        for (int i=0; i<columnas; i++){
            for (int j = 0; j<filas; j++){
                casilla[i][j] = new Mina();
                casilla[i][j].setPrefSize(45, 45);
                root.add(casilla[i][j], i, j);

                jugarPartida();

            }
        }

        //Creación de minas aleatorias
        int minaFila;
        int minaColumna;
        for(int i=0; i < num_minas; i++){
            do {
                minaFila = (int)(Math.random()*filas);
                minaColumna = (int)(Math.random()*columnas);

            } while (casilla[minaColumna][minaFila].isEsMina() == true);

            casilla[minaColumna][minaFila].setEsMina(true);
        }
        //Comprobar si es mina
        for (int i=0; i < columnas; i++) {
            for (int j=0; j<filas; j++) {
                int a = i;
                int b = j;
                /*casilla[i][j].setOnMouseClicked(new EventHandler<> {
                    @Override
                    public void handle(MouseEvent e) {
                        if(e.getButton() == MouseButton.PRIMARY && casilla[a][b].getEstado()==0) {
                            actualizarMina(a,b);
                            casilla[a][b].setCasillaVisible(true);
                        } else if(e.getButton() == MouseButton.SECONDARY && !casilla[a][b].isCasillaVisible()) {
                            marcarMina(a, b);
                        }
                    }
                });
                    //.setOnMouseClicked(mouseEvent -> {
                ;// public void handle(MouseEvent e)
                if (matri_button[a][b].getOnMouseClicked() == MouseButton.PRIMARY && casilla[a][b].getEstado() == 0) {
                    actualizarMina(a, b);
                    casilla[a][b].setCasillaVisible(true);
                    //calcularResultado();
                } else if (e.getButton() == MouseButton.SECONDARY && !casilla[a][b].isCasillaVisible()) {
                    marcarMina(a, b);
                }*/




            }
        }


    }

    public static void actualizarMina(int a , int b){
        if (casilla[a][b].isEsMina()==true){
            for (int i=0; i < columnas; i++){
                for (int j=0; j < filas; j++){
                    if (casilla[i][j].isEsMina()==true){
                        ImageView imagen_mina = new ImageView();
                        imagen_mina.setPreserveRatio(true);
                        imagen_mina.setFitWidth(25);
                        imagen_mina.setImage(new Image(""));
                        casilla[i][j].setStyle("-fx-background-color: #fb0000");
                    }
                }
            }
            finPartida("Pisaste una mina, PERDISTE");
        } else {
            casilla[a][b].setStyle("-fx-background-color: #c7caf3");
            contarAlrededor(a, b);
            casilla[a][b].setText(Integer.toString(casilla[a][b].getContador()));
        }
    }

    public static void marcarMina ( int i, int j){
        ImageView bandera_imagen = new ImageView();
        bandera_imagen.setPreserveRatio(true);
        bandera_imagen.setFitHeight(25);
        casilla[i][j].setGraphic(bandera_imagen);
        switch (casilla[i][j].getEstado()){
            case "0":
                bandera_imagen.setImage(new Image(""));
                casilla[i][j].setStyle("-fx-background-color: #a017c7;");
                casilla[i][j].cambio_estado();
                break;
            case "1":
                bandera_imagen.setImage(new Image("d"));
                casilla[i][j].setStyle("-fx-background-color: #a017c7;");
                casilla[i][j].cambio_estado();
                break;
            case "2":
                casilla[i][j].setGraphic(null);
                casilla[i][j].setStyle("");
                casilla[i][j].cambio_estado();
                break;
        }

    }
    public static void contarAlrededor(int columna, int fila){
        casilla[columna][fila].setContador(0);
        for (int i = 0; i < columnas; i++){
            for (int j = 0; j < filas; j++){
                if ((i == columna||j == (fila-1) || i==(columna+1)) && (j==fila||j==(fila-1)||j==(fila+1))){
                    if (casilla[i][j].isEsMina()){
                        casilla[columna][fila].setContador(casilla[columna][fila].getContador()+1);
                    }

                }
            }
        }
    }

    public static void datos_usuario(){
        Alert inicio = new Alert(Alert.AlertType.CONFIRMATION);
        inicio.setTitle("!Inicio del juego¡");
        inicio.setHeaderText("Elige la dificultad: \nDummy \nAdvance");
        inicio.setContentText("Elige el nivel deseado");

        ButtonType  dummmy_butt = new ButtonType("Dummy");
        ButtonType advance_butt = new ButtonType("Advance");

        inicio.getButtonTypes().setAll(dummmy_butt, advance_butt);

        Optional<ButtonType> result = inicio.showAndWait();
        if (result.get() == dummmy_butt){

            filas = 8;
            columnas = 8;
            num_minas = 5;
            casilla = new Mina[columnas][filas];
        } else if (result.get() == advance_butt) {
            filas = 8;
            columnas = 8;
            num_minas = 10;
            casilla = new Mina[columnas][filas];

        } else {
            System.exit(0);
        }
        root.getChildren().clear();
        root.autosize();





    }
    private static void finPartida (String mensaje) {
        List<String> choices = new ArrayList<>();
        choices.add("Nueva partida");
        choices.add("Cerrar el juego");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Nueva partida", choices);
        dialog.setTitle("Fin de la partida");
        dialog.setHeaderText(mensaje);
        dialog.setContentText("¿Qué desea hacer?");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if (result.get().compareTo("Nueva partida") == 0) {
                repetirPartida(escenario_guard);
            } else {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }








    public static void main(String[] args) {
        launch(args);
    }
}
