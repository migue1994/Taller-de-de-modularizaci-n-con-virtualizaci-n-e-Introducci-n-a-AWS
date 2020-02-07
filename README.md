# Taller de clientes y servicios

Este proyecto tiene como objetivo realizar unos ejercicios que nos permitirán entender mejor como funcionan las herramientas de networking que posee java, además se implementará un servidor web en java, que puede manejar peticiones desde el navegador, en el cual se mostrará el recurso, dependiendo de la url ingresada. Finalmente el servidor web se desplegará en Heroku.

# Empezando

Para descargar este proyecto en algún directorio, empezamos abriendo un ventana de consola, vamos al direcorio donde queremos que quede el proyecto y escribimos:

``$ git clone https://github.com/migue1994/Taller-cliente-servicio-arep.git``

Nos empezará a descargar el contenido dentro de un repositorio local y así poderlo usar.

# Prerrequisitos

Para empezar, debemos asegurarnos que tenemos las herramientas necesarias para poder ejecutar el proyecto (En caso de que no posea alguna de estas herrmientas, puede dar click sobre los nombres que están a continuación y lo redirecionará a un tutorial de instalacion), estas herramientas son:

- [Java JDK](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm#JSJIG-GUID-A740535E-9F97-448C-A141-B95BF1688E6F)
- [Apache Maven](https://howtodoinjava.com/maven/how-to-install-maven-on-windows/)
- [Git](https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)

# Ejecutar el proyecto

Una vez que hayamos descargado el proyecto, abrimos una cmd y accedemos al directorio donde esta el proyecto, una vez allí, ejecutamos el siguiente comando:

``$ mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.servidorweb.HttpServer"``

Una vez se haya ejecutado el comando, se compilará el proyecto y nos aparecerá una pantalla que dice que el servidor esta listo para recibir una petición desde el navegador.


# Herramientas usadas

- [Microsoft Visual Studio Code](https://code.visualstudio.com/)
- [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Apache Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
- [GitHub](https://github.com/)

# Autor

## Miguel Ángel Rivera Rojas

# Licencia:

[LICENSE.txt](LICENSE.txt)

# Documentación

La documentación de este proyecto, podrá encontrarla en la siguiente dirección:

``target\site\apidocs\edu\escuelaing\arem\designprimer``

Si desea generar la documentación nuevamente, solo deberá escribir el siguiente comando desde la consola cmd:

``` $ mvn javadoc:javadoc```

Esto le generará una documentación, que se ubica por defecto en `target\site\apidocs` desde allí podrá abrir el tipo de documentación que quiere revisar de forma directa en el navegador.