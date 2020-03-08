package edu.eci.miguel;

import java.io.*;
import java.net.*;

public class CustomerMain {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://modularizacion-virtualizacion.herokuapp.com/index.html");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}