package com.example.minesweeper3;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import gnu.io.SerialPort;
import gnu.io.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Esta clase es el Main del juego, acá se creará las ventanas, con sus respectivos elementos, además  acá estará también la logica para inicializar cada uno de los modos de juego
 * @author Jose_PabloGD
 * @version 16/04/2023
 */

public class Minesweeper extends Application {
    Random random = new Random();
    Button[][] Matri_bot = new Button[8][8];
    Tablero tablero_game = new Tablero(8, 8);
    boolean turno = true;
    Listas lista_segu = new Listas();
    Listas lista_posi = new Listas();
    Listas lista_gen = new Listas();
    Pila_suge Pila = new Pila_suge();
    int canti_turnos = 0;
    private static final String port_names[] = {"COM4"};
    public Button sug_dum = new Button("Sugerencia");
    Button sug_ad = new Button("Sugerencia");

    private BufferedReader input;
    private OutputStream output;
    private static final int time_out = 2000;
    private static final int data_rate = 9600;
    private SerialPort serialPort;
    private static final int Izq_botton_pin = 8;
    private static final int Der_botton_pin = 7;
    private static final int Up_botton_pin = 6;
    private static final int Down_botton_pin = 5;
    private static final int Izq_click_pin = 4;
    private static final int Der_click_pin = 3;


    /**
     * Se inicia la ventana principal, con botones que al ser presinados crearán sus gridpanes y que llamarán al metodo Inicializar_Juego
     * @param stage Este parametro representa la ventana menu donde estarán los botones que enviarán a los modos de juego
     */

    @Override
    public void start(Stage stage) {
        stage.setTitle("Menú");

        Button dummy_Button = new Button("Modo Dummy");

        dummy_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Pane root = new Pane();
                GridPane gridPane = new GridPane();
                gridPane.setHgap(3);
                gridPane.setVgap(3);
                gridPane.setLayoutX(17);
                gridPane.setLayoutY(27);
                sug_dum.setLayoutX(250);
                sug_dum.setLayoutY(20);
                root.getChildren().addAll(gridPane, sug_ad);

                Scene dummy_scene = new Scene(root, 450, 450);


                Stage ventana_dummy = new Stage();
                ventana_dummy.setScene(dummy_scene);
                ventana_dummy.show();
                stage.close();

                llamar(gridPane);
            }
            public void llamar(GridPane root){
                Inicializar_Juego(root, true);
            }


        });

        sug_dum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Esc_Sugerencia(true);
            }



        });




        Button advance_Button = new Button("Modo Advance");
        advance_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Pane root = new Pane();
                GridPane gridPane = new GridPane();
                gridPane.setHgap(3);
                gridPane.setVgap(3);
                gridPane.setLayoutX(17);
                gridPane.setLayoutY(27);
                sug_ad.setLayoutX(180);
                sug_ad.setLayoutY(0);
                root.getChildren().addAll(gridPane, sug_ad);


                Scene advance_scene = new Scene(root, 450, 450);


                Stage ventana_advance = new Stage();
                ventana_advance.setScene(advance_scene);
                ventana_advance.show();
                stage.close();

                llamar(gridPane);
            }
            public void llamar(GridPane root){
                Inicializar_Juego(root, false);
            }


        });

        sug_ad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Esc_Sugerencia(false);
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

    /**
     * En este metodo se le asigna a cada posición del gridpane una casilla boton y se crea la bandera
     * @param root Utilizamos como parametro la raíz a utilizar en este caso el Gridpane donde posteriormente se colocarán botones
     * @param nivel Este parametro nos ayudará a saber en que nivel nos encontramos si dummy (true) o el advance (false)
     */
    public void Inicializar_Juego(GridPane root, boolean nivel){


        tablero_game.crear_casillas();

        //AtomicBoolean turno = new AtomicBoolean(false);

        //turno.set(true);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Casilla botton = tablero_game.casillas[i][j];
                botton.setStyle("-fx-background-color: red");
                botton.setOnMouseEntered(mouseEvent -> botton.setStyle("-fx-background-color: lightgray"));
                botton.setOnMouseExited(mouseEvent -> botton.setStyle("-fx-background-color: red"));

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
                ImageView imagen = new ImageView(bandera);

                Matri_bot[fila_tmp][columna_tmp].setGraphic(imagen);
                Matri_bot[fila_tmp][columna_tmp].getGraphic().setVisible(false);
                imagen.setFitHeight(40);
                imagen.setFitWidth(20);

                if (nivel == true){
                    System.out.println("Entro a dummy");
                    Turnos(Matri_bot, tablero_game, true, random);
                }else{
                    System.out.println("Entro ad");
                    Turnos_ad(Matri_bot, tablero_game, true, random);
                }



            }
        }

    }



    /**
     * En este metodo, se verifica el turno en el que se encuentra el juego en el modo dummy y se verifica si se esta tocando el click izquierdo (en este dependera si hay una bomba o no en esa casilla) o el click derecho (este sera para colocar la mina). Además si es el turno de la cpu llama al metodo con la logica correspondiente
     * @param Matri_bot Una matriz de botones que se utilizará como un tablero invisible
     * @param tablero_game Parametro de tipo Tablero
     * @param turno Boolean para verifivar si es turno del jugador o del cpu
     * @param random Parametro de tipo Random para crear variables randoms
     */

    public void Turnos(Button[][] Matri_bot, Tablero tablero_game, Boolean turno, Random random){
        //System.out.println(turno);
        canti_turnos++;
        canti_turnos++;
        if (canti_turnos>=5){
            sug_dum.setDisable(false);
        }else sug_dum.setDisable(true);
        Agre_Sugerencias();
        if (turno == true){
            System.out.println("GJGJGJGJGJ");
            for (int i = 0; i<8; i++){
                for (int j = 0; j<8; j++){
                    int fila = i;
                    int columna = j;

                    Random rand_final = random;
                    //Matri_bot[i][j].setGraphic(null);

                    int I_F = i;
                    int J_F = j;

                    Matri_bot[i][j].setOnMouseClicked((MouseEvent event) -> {
                        int FilA = GridPane.getColumnIndex(Matri_bot[fila][columna]);
                        int ColumnA = GridPane.getRowIndex(Matri_bot[fila][columna]);

                        if (event.getButton() == MouseButton.PRIMARY){
                            //Matri_bot[FilA][ColumnA].setDisable(true);


                            if (tablero_game.casillas[FilA][ColumnA].hay_Mina()){



                                String mensaje;
                                Matri_bot[I_F][J_F].setGraphic(null);

                                for (int f = 0; f < 8; f++){
                                    for ( int jj = 0; jj < 8;  jj++){
                                        Matri_bot[f][jj].setDisable(true);

                                        Image bomba = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\Mina image (1).png");
                                        ImageView imagen = new ImageView(bomba);
                                        Matri_bot[fila][columna].setGraphic(imagen);
                                        imagen.setFitHeight(40);
                                        imagen.setFitWidth(20);
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


                            if (Matri_bot[fila][columna].getGraphic().isVisible()){
                                Matri_bot[fila][columna].getGraphic().setVisible(false);

                            }else {
                                Matri_bot[fila][columna].getGraphic().setVisible(true);
                            }

                        }
                    });
                }
            }
            //Turnos(Matri_bot, tablero_game, false,random );
        }else {
            turno_cpu(Matri_bot, tablero_game, turno,random );
        }
    }

    public void Agre_Sugerencias(){
        int Rfila = (int)(Math.random()*8);
        int Rcolumna = (int)(Math.random()*8);

        while(tablero_game.casillas[Rfila][Rcolumna].hay_Mina() || tablero_game.casillas[Rfila][Rcolumna].isAbierta()){
            Rfila = (int)(Math.random()*8);
            Rcolumna = (int)(Math.random()*8);

        }
        if (Pila.getTop()<5){
        int[] Sug_node = {Rfila,Rcolumna};
        Node sug_node = new Node(Sug_node);
        Pila.push(sug_node);
    }}
    public void Esc_Sugerencia( Boolean nivel){
        canti_turnos = canti_turnos -5;
        Node ref = Pila.peek();
        Pila.pop();
        int fila = ref.get_I();
        int columna = ref.get_J();
        tablero_game.revelar_tablero(fila, columna);
        if(nivel){
            Turnos(Matri_bot, tablero_game, false, random);
        }else {
            Turnos_ad(Matri_bot, tablero_game, false, random);
        }
    }






    /**
     * En este metodo igualmente se revisa el turno en el que se encuentra en el nivel advance y cuando es el turno de la cpu llama al metodo con la logica correspondiente
     * @param Matri_bot Una matriz de botones que se utilizará como un tablero invisible
     * @param tablero_game Parametro de tipo Tablero
     * @param turno Boolean para verifivar si es turno del jugador o del cpu
     * @param random Parametro de tipo Random para crear variables randoms
     */
    public void Turnos_ad(Button[][] Matri_bot, Tablero tablero_game, Boolean turno, Random random){
        canti_turnos++;
        if (canti_turnos>=5){
            sug_ad.setDisable(false);
        }else sug_ad.setDisable(true);
        Agre_Sugerencias();
        System.out.println("entro a turnos_ad"+turno);
        if (turno == true){

            for (int i = 0; i<8; i++){
                for (int j = 0; j<8; j++){
                    int fila = i;
                    int columna = j;

                    Random rand_final = random;
                    //Matri_bot[i][j].setGraphic(null);

                    int I_F = i;
                    int J_F = j;

                    Matri_bot[i][j].setOnMouseClicked((MouseEvent event) -> {
                        int FilA = GridPane.getColumnIndex(Matri_bot[fila][columna]);
                        int ColumnA = GridPane.getRowIndex(Matri_bot[fila][columna]);

                        if (event.getButton() == MouseButton.PRIMARY){
                            //Matri_bot[FilA][ColumnA].setDisable(true);


                            if (tablero_game.casillas[FilA][ColumnA].hay_Mina()){



                                String mensaje;
                                Matri_bot[I_F][J_F].setGraphic(null);

                                for (int f = 0; f < 8; f++){
                                    for ( int jj = 0; jj < 8;  jj++){
                                        Matri_bot[f][jj].setDisable(true);

                                        Image bomba = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\Mina image (1).png");
                                        ImageView imagen = new ImageView(bomba);
                                        Matri_bot[fila][columna].setGraphic(imagen);
                                        imagen.setFitHeight(40);
                                        imagen.setFitWidth(20);
                                    }
                                }

                                mensaje = "PERDISTE";
                                mostrarMensaje(mensaje);
                            }else{
                                Matri_bot[fila][columna].setDisable(true);
                                tablero_game.contarMinasAdyacentes();
                                if (tablero_game.casillas[FilA][ColumnA].getMinas_Adyacentes() != 0){

                                    tablero_game.casillas[FilA][ColumnA].setText(tablero_game.casillas[FilA][ColumnA].getMinas_Adyacentes()+"");
                                }
                                tablero_game.revelar_tablero(fila, columna);
                                Turnos_ad(Matri_bot, tablero_game, false, rand_final);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {


                            if (Matri_bot[fila][columna].getGraphic().isVisible()){
                                Matri_bot[fila][columna].getGraphic().setVisible(false);

                            }else {
                                Matri_bot[fila][columna].getGraphic().setVisible(true);
                            }

                        }
                    });
                }
            }
        }else {
            turno_cpuAd(Matri_bot, tablero_game,random );
        }
    }

    /**
     *En este metodo esta la logica del turno de la cpu en modo advance, se hace presente el uso de listas donde la cpu escogera entre casillas: generales, posibles y seguras. En este metodo la logica hace demasiado dificil poder ganar
     * @param Matri_bot Una matriz de botones que se utilizará como un tablero invisible
     * @param tablero Parametro de tipo Tablero
     * @param random Parametro de tipo Random para crear variables de este tipo
     */

    public void turno_cpuAd(Button [][] Matri_bot, Tablero tablero, Random random){
        canti_turnos++;
        if (canti_turnos>=5){
            sug_ad.setDisable(false);
        }else sug_ad.setDisable(true);
        Agre_Sugerencias();
        System.out.println("Entra a cpuAd");
        lista_gen.eliminar();
        lista_posi.eliminar();
        lista_segu.eliminar();

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(tablero.casillas[i][j].isAbierta()==false){
                    int[] nodito = new int[2];
                    nodito[0] = i;
                    nodito[1] = j;
                    com.example.minesweeper3.Node Nodito = new com.example.minesweeper3.Node(nodito);
                    lista_gen.agregar(Nodito);
                }
            }
        }
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(lista_gen.buscar(i,j) != null){
                    if(!tablero.casillas[i][j].hay_Mina() && !lista_gen.buscar(i,j).get_Sure()){
                        int[] nodito = new int[2];
                        nodito[0] = i;
                        nodito[1] = j;
                        com.example.minesweeper3.Node Nodito = new com.example.minesweeper3.Node(nodito);
                        lista_segu.agregar(Nodito);
                        Nodito.setSure();
                    }else if (tablero.casillas[i][j].hay_Mina() && !lista_gen.buscar(i,j).get_Posib()){
                        int[] nodito = new int[2];
                        nodito[0] = i;
                        nodito[1] = j;
                        com.example.minesweeper3.Node Nodito = new com.example.minesweeper3.Node(nodito);
                        lista_posi.agregar(Nodito);
                        Nodito.setPosib();
                    }
                }

            }
        }
        System.out.println("Tamaño segu"+lista_segu.size());
        System.out.println("Segura mor: "+lista_segu);
        System.out.println("Posible mor: "+lista_posi);

        if (lista_segu.size() <= 0){
            System.out.println("Hay bomba");
            com.example.minesweeper3.Node Nodote_ref = lista_posi.buscarAleatorio();
            int[] nodote_ref = new int[2];
            nodote_ref[0] = Nodote_ref.get_I();
            nodote_ref[1] = Nodote_ref.get_J();

            int x_ref = nodote_ref[0];
            int y_ref = nodote_ref[1];

            if (tablero.casillas[x_ref][y_ref].hay_Mina()){
                String mensaje;
                Matri_bot[x_ref][y_ref].setDisable(true);
                Matri_bot[x_ref][y_ref].setGraphic(null);
                Matri_bot[x_ref][y_ref].setStyle("-fx-background-color: aqua");

                for (int f = 0; f < 8; f++){
                    for ( int jj = 0; jj < 8;  jj++){
                        Matri_bot[f][jj].setDisable(true);

                        Image bomba = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\Mina image (1).png");
                        ImageView imagen = new ImageView(bomba);
                        Matri_bot[x_ref][y_ref].setGraphic(imagen);
                        imagen.setFitHeight(40);
                        imagen.setFitWidth(20);
                    }
                }

                mensaje = "FELICIDADES, le has ganado a la cpu!!";
                mostrarMensaje(mensaje);
            }

        }else{
            System.out.println("No hay bomba, se supone");
            com.example.minesweeper3.Node Nodote_refo = lista_segu.buscarAleatorio();
            int[] nodote_refo = new int[2];
            nodote_refo[0] = Nodote_refo.get_I();
            nodote_refo[1] = Nodote_refo.get_J();

            int x_refo = nodote_refo[0];
            int y_refo = nodote_refo[1];
            Matri_bot[x_refo][y_refo].setDisable(true);
            tablero.contarMinasAdyacentes();
            if (tablero.casillas[x_refo][y_refo].getMinas_Adyacentes() != 0){
                System.out.println("Entro al if");

                tablero.casillas[x_refo][y_refo].setText(tablero.casillas[x_refo][y_refo].getMinas_Adyacentes()+"");
            }
            tablero.revelar_tablero(x_refo, y_refo);
            Turnos_ad(Matri_bot, tablero, true, random);
        }

    }

    /**
     * En este metodo se encuentra la logica del turno de la cpu en modo dummy, en esta logica la cpu escogera de manera aleatoria entre todas las casillas por lo que hace que con mayor probabilidad la cpu pierda
     * @param Matri_bot Una matriz de botones que se utilizará como un tablero invisible
     * @param tablero Parametro de tipo Tablero
     * @param turno Boolean para verifivar si es turno del jugador o del cpu
     * @param random Parametro de tipo Random para crear variables randoms
     */
    public void turno_cpu(Button[][] Matri_bot,Tablero tablero, boolean turno, Random random ){
        canti_turnos++;
        canti_turnos++;
        if (canti_turnos>=5){
            sug_dum.setDisable(false);
        }else sug_dum.setDisable(true);
        Agre_Sugerencias();
        int random1 = random.nextInt(8);
        int random2 = random.nextInt(8);



                while (tablero.casillas[random1][random2].isAbierta()){
                    random = new Random();
                    random1 = random.nextInt(8);
                    random2 = random.nextInt(8);
                }
                int Fil = GridPane.getColumnIndex(Matri_bot[random1][random2]);
                int Column = GridPane.getRowIndex(Matri_bot[random1][random2]);
                if (tablero.casillas[Fil][Column].hay_Mina()){

                    String mensaje;
                    Matri_bot[Fil][Column].setDisable(true);
                    Matri_bot[Fil][Column].setGraphic(null);
                    Matri_bot[Fil][Column].setStyle("-fx-background-color: aqua");

                    for (int f = 0; f < 8; f++){
                        for ( int jj = 0; jj < 8;  jj++){
                            Matri_bot[f][jj].setDisable(true);

                            Image bomba = new Image("C:\\Users\\35087\\IdeaProjects\\Ejercicios\\Minesweeper3\\src\\main\\resources\\com\\example\\minesweeper3\\Mina image (1).png");
                            ImageView imagen = new ImageView(bomba);
                            Matri_bot[Fil][Column].setGraphic(imagen);
                            imagen.setFitHeight(40);
                            imagen.setFitWidth(20);
                        }
                    }

                    mensaje = "FELICIDADES, le has ganado a la cpu!!";
                    mostrarMensaje(mensaje);
                }else{

                    tablero.contarMinasAdyacentes();
                    if (tablero.casillas[Fil][Column].getMinas_Adyacentes() != 0){

                        tablero.casillas[Fil][Column].setText(tablero.casillas[Fil][Column].getMinas_Adyacentes()+"");
                    }
                    tablero.revelar_tablero(Fil, Column);
                    Turnos(Matri_bot, tablero, true, random);
                }




        Turnos(Matri_bot, tablero, true,random );
    }




    /**
     * En este metodo se crea un una alerta la cual será utilizada en otros metodos, la información que mostrará dependerá de la logica de los otros metodos
     * @param mensaje_final String a mostrar dependiendo de lo que se solicite en otros metodos
     */

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
