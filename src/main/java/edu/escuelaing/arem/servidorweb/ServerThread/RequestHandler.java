package edu.escuelaing.arem.servidorweb.ServerThread;

import edu.escuelaing.arem.servidorweb.DataBase.DataBase;
import edu.escuelaing.arem.servidorweb.load.LoadAll;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class RequestHandler extends Thread{
    private LoadAll la;
    Socket cc;

    /**
     * Constructor of the class Request Handler
     * @param cc The client socket
     * @param la Class that load all resources
     */
    public RequestHandler(Socket cc, LoadAll la){
        this.cc = cc;
        this.la = la;
    }

    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        try {
            attendRequest();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime);
    }

    /**
     * Attend the request of the customer
     * @throws IOException throws IOException
     */
    public void attendRequest() throws IOException {
        PrintWriter out = new PrintWriter(cc.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(cc.getInputStream()));

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
                }
                else if(la.isAResource(resource)){
                    out.println("HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: text/html\r\n\r\n");
                    out.println(la.loadResource(resource));
                }else{
                    readFile(out, cc.getOutputStream() ,resource);
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
        cc.close();
    }

    /**
     * Reads the file depending on the extension of the file
     * @param out Printwriter that prints the html code in the browser
     * @param ost Output stream
     * @param resource Resource to be load
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

}