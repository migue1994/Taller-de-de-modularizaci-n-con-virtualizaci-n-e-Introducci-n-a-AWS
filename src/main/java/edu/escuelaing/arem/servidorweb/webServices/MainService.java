package edu.escuelaing.arem.servidorweb.webServices;

import edu.escuelaing.arem.servidorweb.annotations.Web;

public class MainService {
    @Web("/message.html")
    /**
     * Atiende la petición para el recurso solicitado, según la anotacion
     * @return Código html
     */
    public static String mainPage() {
        return "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Title of the document</title>\n"
                + "</head>\n" + "<body>\n" + "<h1 style=\"text-align: center;\">Pagina principal</h1>" + "</body>\n" + "</html>\n";
    }
}
