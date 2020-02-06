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
            System.out.println(resource);
            if (!resource.equals("/favicon.ico")) {
                if(!resource.equals("/")){
                    readFile(out, clientSocket.getOutputStream() ,resource);
                }else{
                    outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                    + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Title of the document</title>\n"
                    + "</head>\n" + "<body>\n" + "<h1>Pagina principal</h1>" + "</body>\n" + "</html>\n" + inputLine;
                    out.println(outputLine);
                }
            }

           
            out.close();
            in.close();
            clientSocket.close();
            
        }
    }

    private static void readFile(PrintWriter out, OutputStream ost ,String resource) throws IOException {

        if (resource.contains(".PNG")) {
            readImage(out, ost, resource);
        } else {
            getFile(out, resource);
        }

    }

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

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}