package edu.escuelaing.arem.url;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.net.URL;
import java.io.*;

public class UrlPuntoDos extends Exception	{
    private static final long serialVersionUID = 1L;

    public static void main(String args[]) throws IOException {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 

        String url = input.readLine();

        URL urlInput = new URL(url);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlInput.openStream()))) {
            String inputLine = null;
           while ((inputLine = reader.readLine()) != null) {
                whenAppendStringUsingBufferedWritter_thenOldContentShouldExistToo(inputLine);
            }
           
          } catch (IOException x) {
            System.err.println(x);
           
          }
         
    }

    public static void whenAppendStringUsingBufferedWritter_thenOldContentShouldExistToo(String data) 
    throws IOException {
      BufferedWriter writer = new BufferedWriter(new FileWriter("prueba2.html", true));
      writer.append(' ');
      writer.append(data);
       
      writer.close();
  }
}