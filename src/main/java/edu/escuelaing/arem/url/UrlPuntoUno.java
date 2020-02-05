package edu.escuelaing.arem.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlPuntoUno{
    public static void main(String args[]) throws Exception{
        URL google = new URL("http://www.google.com/");
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()))) {
            String inputLine = null;
           while ((inputLine = reader.readLine()) != null) {
            System.out.println(inputLine);
           }

           System.out.println("Google Protocol");
           System.out.println(google.getProtocol());

           System.out.println("Google Authority");
           System.out.println(google.getAuthority());

           System.out.println("Google Host");
           System.out.println(google.getHost());

           System.out.println("Google Port");
           System.out.println(google.getPort());

           System.out.println("Google Query");
           System.out.println(google.getQuery());

           System.out.println("Google Ref");
           System.out.println(google.getRef());

           System.out.println("Google File");
           System.out.println(google.getFile());

           System.out.println("Google Path");
           System.out.println(google.getPath());

           
          } catch (IOException x) {
           System.err.println(x);
           
          }
    }

}