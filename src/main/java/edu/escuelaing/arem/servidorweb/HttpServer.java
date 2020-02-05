package edu.escuelaing.arem.servidorweb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            int cont = 0;
            String[] resource = new String[3];

            while ((inputLine = in.readLine()) != null) {
                if (cont == 0) {
                    resource = inputLine.split(" ");
                    cont++;
                }
                System.out.println(inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            if (!resource[1].equals("/favicon.ico") && !resource[1].equals("/")) {
                outputLine = readFile(resource[1]) + inputLine;
                out.println(outputLine);
            }else{
                outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Title of the document</title>\n"
                + "</head>\n" + "<body>\n" + "<h1>Página de inicio</h1>\n" + "</body>\n" + "</html>\n" + inputLine;
                out.println(outputLine);
            }

           
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
    }

    private static String readFile(String resource) throws IOException {
        String st = "";
        st = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";

        if (resource.contains(".PNG") || resource.contains(".jpg") || resource.contains(".jpeg")) {
            st += getImagen(resource);
        } else {
            st += getFile(resource);
        }

        return st;
    }

    public static String getImagen(String resource) {
        String a = "<!DOCTYPE html>" + "<html lang='en'>" + "<head>" + "<meta charset='UTF-8'>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<meta http-equiv='X-UA-Compatible' content='ie=edge'>" + "<title>Image</title>" + "</head>"
                + "<body>" + "<img resources" + resource + "/>" + "</body>" + "</html>";

        return a;
    }

    public static String getFile(String resource) throws IOException {
        String st;
        String res = "";
        File file = new File("resources" + resource);
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((st = br.readLine()) != null){
            res += st;
        }

        br.close();

        return res;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}