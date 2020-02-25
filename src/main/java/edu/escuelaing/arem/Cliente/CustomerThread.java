package edu.escuelaing.arem.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomerThread extends Thread {

    private String url;

    /**
     * Constructor of the class
     * @param url String that contains the url for request
     */
    public CustomerThread(String url){
        this.url = url;
    }

    @Override
    public void run() {
        super.run();
        try {
            startCustomer();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform the request to the given url
     * @throws MalformedURLException
     */
    public void startCustomer() throws MalformedURLException {
        URL url = new URL(this.url);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
