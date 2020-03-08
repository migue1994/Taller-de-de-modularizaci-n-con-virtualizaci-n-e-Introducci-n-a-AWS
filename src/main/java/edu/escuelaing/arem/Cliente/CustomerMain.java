package edu.escuelaing.arem.Cliente;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerMain {

    public static List<CustomerThread> threads = new ArrayList();
    public static String[] urls = new String[]{"Example1.html", "index.html", "JsExample.js", "paisaje1.jpg", "paisaje2.jpg"};

    public static void main(String[] args) throws Exception {
        int numClients = readNumberOfClients();
        String urlBase = "https://modularizacion-virtualizacion.herokuapp.com/";
        createThreads(numClients, urls.length, urlBase);

    }

    private static void createThreads(int numClients, int clients, String urlBase) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Random rand = new Random();
        int random_int = rand.nextInt(clients);
        String url = urlBase + urls[random_int];
        for (int i = 0; i < numClients; i++) {
            threads.add(new CustomerThread(url));
        }
        threads.forEach(t->executor.execute(t));
        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static int readNumberOfClients() throws IOException {
        System.out.println("How many clients do you want?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String num = reader.readLine();
        return Integer.parseInt(num);
    }
}