package com.example.minesweeper3;


import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.PrimitiveIterator;

public class Arduino {

    private static final String port_names[] = {"COM3"};

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

    public void comu_serial(){
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //Iteramos hasta encontrar el puerto que concuerde con el nombre
        while (portEnum.hasMoreElements()){
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : port_names){
                if(currPortId.getName().equals(portName)){
                    portId = currPortId;
                    break;
                }
            }
        }
        if(portId == null){
            System.out.println("No llego al puerto");
            return;
        }
        try{
            //Se abre el puerto serial y se establece los parametros
            serialPort = (SerialPort) portId.open(this.getClass().getName(), time_out);
            serialPort.setSerialPortParams(data_rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            //obtenemos objetos de entrada y salida de la comu_serial
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            //Se espera a que la placa inicie
            Thread.sleep(2000);
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

    /*private boolean Izq_Pres(){

    }*/

}
