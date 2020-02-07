package edu.escuelaing.arem.datagramas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que realiza las peticiones del cliente por medio de datagramas
 */
public class DatagramTimeClient extends Thread {

    private static String received;
    /**
     * Calse principal
     * @param args argumentos
     */
    public static void main(String[] args) {
        while (true) {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            DatagramTimeClient dtc = new DatagramTimeClient();
            pool.execute(dtc);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.shutdown();
            System.out.println(received);
        }
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            DatagramTimeClient.received = new String(packet.getData(), 0, packet.getLength());
            //System.out.println("Date: " + received);
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}