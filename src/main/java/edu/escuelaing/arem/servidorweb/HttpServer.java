package edu.escuelaing.arem.servidorweb;

import edu.escuelaing.arem.servidorweb.ServerThread.RequestHandler;
import edu.escuelaing.arem.servidorweb.load.LoadAll;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer{

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int numThread = 10;
    private static LoadAll la;

    private static ExecutorService pool = Executors.newFixedThreadPool(numThread);

    /**
     * Clase principal de la base del servidor web
     * @param args argumentos
     * @throws IOException Io exception
     */
    public static void main(String[] args) throws IOException {
        la = new LoadAll();
        la.start();
        doConect();
        listen();
    }
    /**
     * Realiza la conexión a la base de datos
     */
    private static void doConect() {

        serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
    }
    /**
     * Se encarga de recibir las peticiones que son hechas por el navegador
     * @throws IOException
     */
    private static void listen() throws IOException {
        while (true) {
            clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
                RequestHandler rh = new RequestHandler(clientSocket, la);
                pool.execute(rh);

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
    }

    /**
     * Permite leer el puerto, para poder desplegar la aplicación en heroku
     * @return Puerto de salida
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}