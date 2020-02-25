package edu.escuelaing.arem.servidorweb.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.escuelaing.arem.servidorweb.Entity.Persona;

/**
 * Clase ques se encarga de administrar la base de datos
 */
public class DataBase {

    private static Connection con;
    public List<Persona> personas;
    /**
     * Constructor
     * @throws SQLException SQL excepción
     */
    public DataBase() throws SQLException {
        connect();
        personas = getPersonas(con);
    }

    /**
     * Se encarga de realizar la conexión a la base de datos de heroku
     */
    public void connect(){
        try {
            String url="jdbc:postgresql://ec2-52-203-160-194.compute-1.amazonaws.com:5432/db0o9evuc5o38f";
            String user="ixzykbxubqfuth";
            String pwd="40e6bb537a0293897bcf5fb1fc9665c60d991817f6de9b19efe45e157e311790";

            con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
            
        }catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * @param con Puente de conexión a la base de datos
     * @return Una lista que contiene todas las personas que están registradas en la base de datos
     * @throws SQLException Excepción sql
     */
    public List<Persona> getPersonas(Connection con) throws SQLException {
        PreparedStatement getAll = null;
        List<Persona> personas = new ArrayList<>();

        getAll = con.prepareStatement("select * from persona");
        ResultSet resultSet = getAll.executeQuery();

        while(resultSet.next()){
            Persona p = new Persona();
            p.setNombres(resultSet.getString("nombres"));
            p.setApellidos(resultSet.getString("apellidos"));
            p.setEdad(resultSet.getInt("edad"));
            p.setTelefono(resultSet.getString("telefono"));
            personas.add(p);
        }
        return personas;
    }

    /**
     * Se encarga de construir la pagina web que se mostrará en el navegador
     * @return String con el código html de la página
     */
    public String returnHtml(){
        String html = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    +"<html lang=\"en\">"
                    +   "<head>"
                    +       "<meta charset=\"UTF-8\">"
                    +       "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    +       "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">"
                    +       "<title>DataBase</title>"
                    +   "</head>"
                    +   "<body>"
                    +       "<h1 style=\"text-align: center;\">Base de datos en Heroku</h1></br>"
                    +       "<table style=\"border: 2px solid black; text-align: center; margin: auto;\">"
                    +           "<tr>"
                    +               "<th style=\"border: 1px solid black;\">Nombres</th>"
                    +               "<th style=\"border: 1px solid black;\">Apellidos</th>"
                    +               "<th style=\"border: 1px solid black;\">Edad</th>"
                    +               "<th style=\"border: 1px solid black;\">Telefono</th>"
                    +           "</tr>"
                    +           dates()
                    +       "</table>"
                    +"</html>";

        return html;
    }

    /**
     * Construye el cuerpo de la tabla que se mostrá en la pagina web
     * @return El html del cuerpo de la tabla
     */
    public String dates(){
        String s = "";
        for(int i = 0; i < personas.size(); i++){
            Persona p = personas.get(i);
            s += "<tr>"
              +      "<td style=\"border: 1px solid black;\">"+p.getNombres()+"</td>"
              +      "<td style=\"border: 1px solid black;\">"+p.getApellidos()+"</td>"
              +      "<td style=\"border: 1px solid black;\">"+p.getEdad()+"</td>"
              +      "<td style=\"border: 1px solid black;\">"+p.getTelefono()+"</td>"
              +  "</tr>";
        }
        return s;
    }
}