package edu.escuelaing.arem.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerMain {
    /**
     * Main method
     * @param args no args
     */
    public static void main(String[] args) {
        int numCustomers = readCustomers();
        int numThreads = readThreads();

        executeCustomers(numThreads, numCustomers);
    }

    /**
     * Make multiple request, depending on the number of threads entered
     * @param numThreads Number of threads needed for the operation
     * @param numCustomers Number of customers needed from the operation
     */
    private static void executeCustomers(int numThreads, int numCustomers) {
        ExecutorService pool = Executors.newFixedThreadPool(numThreads);
        String url = "https://cliente-servicio.herokuapp.com/";
        String[] resources = {"Example1.html","index.html","JsExample.js","paisaje1.jpg","paisaje2.jpg"};
        for (int i = 0; i < numCustomers; i++){
            String resource = getResource(resources);
            CustomerThread ct = new CustomerThread(url + resource);
            pool.execute(ct);
        }
    }

    /**
     * Fetch a resource from a resources list randomly
     * @param resources resources list
     * @return A resource from the resources list randomly
     */
    private static String getResource(String[] resources){
        Random rand = new Random();
        int index = rand.nextInt(resources.length);
        return resources[index];
    }

    /**
     * Reads the number of threads required for do the operation
     * @return The number of threads
     */
    private static int readThreads() {
        System.out.println("How many threads do you want to be active?");
        BufferedReader threads = new BufferedReader(new InputStreamReader(System.in));
        int numThreads = 0;
        try {
            numThreads = Integer.parseInt(threads.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numThreads;
    }

    /**
     * Reads the number of customers required for do the operation
     * @return The number of customers
     */
    private static int readCustomers() {
        System.out.println("How many customers do you want?");

        BufferedReader customers = new BufferedReader(new InputStreamReader(System.in));
        int numCustomers = 0;
        try {
            numCustomers = Integer.parseInt(customers.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numCustomers;
    }
}
