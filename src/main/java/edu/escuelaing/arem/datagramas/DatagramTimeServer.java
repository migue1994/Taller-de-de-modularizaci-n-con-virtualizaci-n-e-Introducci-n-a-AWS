package edu.escuelaing.arem.datagramas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se encarga de correr el servidor que recibe los datagramas
 */
public class DatagramTimeServer {

    DatagramSocket socket;

    /**
     * Constructor
     */
    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta el servidor y crea el espacio para recibir el datagrama
     */
    public void startServer() {
        byte[] buf = new byte[256];
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String dString = new Date().toString();
            buf = dString.getBytes();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);

        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.close();
    }

    /**
     * Método principal
     * @param args argumentos
     */
    public static void main(String[] args){
        while(true){
            DatagramTimeServer ds = new DatagramTimeServer();
            ds.startServer();
        }
    }
}