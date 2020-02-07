package edu.escuelaing.arem.servidorweb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class HttpServer {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    /**
     * Clase principal de la base del servidor web
     * @param args argumentos
     * @throws IOException Io exception
     */
    public static void main(String[] args) throws IOException {
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
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;

            String resource = "/";

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.matches("(GET)+.*")) {
                    resource = inputLine.split(" ")[1];
                }
                System.out.println(inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if (!resource.equals("/favicon.ico")) {
                if (!resource.equals("/")) {
                    if (resource.equals("/DataBase")) {
                        try {
                            DataBase db = new DataBase();
                            outputLine = db.returnHtml();
                            out.println(outputLine);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        
                    }else{
                        readFile(out, clientSocket.getOutputStream() ,resource);
                    }
                    
                    
                }else{
                    outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                    + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Title of the document</title>\n"
                    + "</head>\n" + "<body>\n" + "<h1 style=\"text-align: center;\">Pagina principal</h1>" + "</body>\n" + "</html>\n" + inputLine;
                    out.println(outputLine);
                }
            }

           
            out.close();
            in.close();
            clientSocket.close();
            
        }
    }
    /**
     * Permite leer el reurso solocitado al servidor, dependiendo del tipo de extensión
     * @param out Permite mostrar el contenido en el navegador
     * @param ost Canal que permite el flujo de datos hacia el servidor
     * @param resource Recurso solicitado del navegador
     * @throws IOException
     */
    private static void readFile(PrintWriter out, OutputStream ost ,String resource) throws IOException {

        if (resource.contains(".jpg")) {
            readImage(out, ost, resource);
        } else {
            getFile(out, resource);
        }

    }

    /**
     * 
     * @param out Permite mostrar el contenido en el navegador
     * @param resource Recurso solicitado del navegador
     * @throws IOException Io exception
     */
    public static void getFile(PrintWriter out, String resource) throws IOException { 
        String st;
        String res = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
        File file = new File("src/main/resources" + resource);
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((st = br.readLine()) != null){
            res += st;
        }

        br.close();

        out.println(res);
    }

    /**
     * 
     * @param out Permite mostrar el contenido en el navegador
     * @param outStream Canal que permite el flujo de datos hacia el servidor
     * @param request Recurso solicitado del navegador
     * @throws IOException
     */
    private static void readImage(PrintWriter out, OutputStream outStream, String request) throws IOException {
        File graphicResource= new File("src/main/resources" +request);
        FileInputStream inputImage = new FileInputStream(graphicResource);
        byte[] bytes = new byte[(int) graphicResource.length()];
        inputImage.read(bytes);

        DataOutputStream binaryOut;
        binaryOut = new DataOutputStream(outStream);
        binaryOut.writeBytes("HTTP/1.1 200 OK \r\n");
        binaryOut.writeBytes("Content-Type: image/png\r\n");
        binaryOut.writeBytes("Content-Length: " + bytes.length);
        binaryOut.writeBytes("\r\n\r\n");
        binaryOut.write(bytes);
        binaryOut.close();
        inputImage.close();
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