package edu.escuelaing.arem.servidorweb.webServices;

import edu.escuelaing.arem.servidorweb.annotations.Web;

public class JsService {
    /**
     * Atiende la petición para el recurso solicitado
     * @return Código html correspondiente
     */
    @Web("/js.html")
    public static String getJS(){
        return "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                + "<html>" +
                "<body>"
                + "<h2>Javascript page</h2>"
                + "<p>Strings are written with quotes.</p>"
                + "<p>Numbers are written without quotes.</p>"
                + "<p id=\"demo\"></p>"
                + "<script src=\"JsExample.js\"></script>"
                + "</body>"
                + "</html>";
    }
}
