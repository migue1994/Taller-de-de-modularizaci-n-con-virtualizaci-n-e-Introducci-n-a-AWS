package edu.escuelaing.arem.servidorweb.load;

import edu.escuelaing.arem.servidorweb.annotations.Web;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class LoadAll {
    private HashMap<String, Method> classMethods;

    /**
     * Constructor de la clase
     */
    public LoadAll(){
        this.classMethods = new HashMap<>();
    }

    /**
     * Inicia el proceso de listar los métodos que existen de cada clase
     */
    public void start(){
        String packageClass = "edu.escuelaing.arem.servidorweb.webServices";
        Reflections rf = new Reflections(packageClass, new SubTypesScanner(false));

        Object[] objects = rf.getSubTypesOf(Object.class).toArray();

        for (Object o: objects){
            try {
                Class cl = Class.forName(getClass(o));
                for (Method m : cl.getDeclaredMethods()){
                    if (m.isAnnotationPresent(Web.class)){
                        classMethods.put(m.getAnnotation(Web.class).value(), m);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica si el recurso esta disponible dentro de las anotaciones
     */
    public boolean isAResource (String resource){
        return classMethods.containsKey(resource);
    }

    /**
     * Carga el recurso, en caso de que este exista
     * @param input Valor del recurso
     * @return String con la pagina web
     */
    public String loadResource(String input){
        String resource = null;
        try {
            resource = (String) classMethods.get(input).invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resource;
    }

    /**
     * Permite obtener el nombre como cadena de la clase
     * @param o Objeto que corresponde a una clase
     * @return String que posee el nombre de la clase
     */
    private String getClass(Object o) {
        String[] s = o.toString().split(" ");
        return s[1];
    }

}
