package edu.escuelaing.arem.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(35000);
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }
        Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                double numero = Double.valueOf(inputLine);
                double respuesta = Math.pow(numero, 2);
                System.out.println("El número ingresado es: "+numero);
                outputLine = "Respuesta:" + respuesta;
                System.out.println(outputLine);
                out.println(outputLine);
                if (outputLine.equals("Respuesta: Bye.")) break;
                }
            out.close(); in.close();
            clientSocket.close();
            serverSocket.close();
        }
   }