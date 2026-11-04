package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Permite cargar las rutas de los archivos desde config.properties.
 */
public class Configuracion {

    private Properties propiedades = new Properties();
    private String directorioProyecto;

    /**
     * Carga el archivo de configuración y localiza la carpeta del proyecto.
     *
     * @throws IOException si ocurre un error al localizar o leer la configuración
     */
    public Configuracion() throws IOException {

        directorioProyecto = buscarDirectorioProyecto();

        try (InputStream archivo = getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (archivo == null) {
                throw new FileNotFoundException("No se ha encontrado config.properties");
            }

            propiedades.load(archivo);
        }
    }

    /**
     * Busca la carpeta raíz del módulo a partir de la ubicación de las clases compiladas.
     *
     * @return ruta absoluta de la carpeta del proyecto
     * @throws IOException si no se encuentra la carpeta del proyecto
     */
    private String buscarDirectorioProyecto() throws IOException {

        try {
            if (getClass().getProtectionDomain().getCodeSource() == null) {
                throw new FileNotFoundException("No se ha podido localizar la carpeta del proyecto.");
            }

            File carpeta = new File(
                    getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
            );

            if (!carpeta.isDirectory()) {
                carpeta = carpeta.getParentFile();
            }

            while (carpeta != null) {

                File pom = new File(carpeta, "pom.xml");
                File datos = new File(carpeta, "datos");

                if (pom.isFile() && datos.isDirectory()) {
                    return carpeta.getAbsolutePath();
                }

                carpeta = carpeta.getParentFile();
            }

        } catch (URISyntaxException e) {
            throw new IOException("No se ha podido localizar la carpeta del proyecto.", e);
        }

        throw new FileNotFoundException(
                "No se ha encontrado la carpeta del proyecto con pom.xml y datos."
        );
    }

    /**
     * Obtiene una ruta del archivo de configuración y la convierte en absoluta.
     *
     * @param clave nombre de la propiedad que contiene la ruta
     * @return ruta absoluta del archivo
     */
    private String obtenerRuta(String clave) {

        String ruta = propiedades.getProperty(clave);

        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalStateException("No se ha configurado la ruta: " + clave);
        }

        File archivo = new File(ruta.trim());

        if (!archivo.isAbsolute()) {
            archivo = new File(directorioProyecto, ruta.trim());
        }

        return archivo.getAbsolutePath();
    }

    /** @return ruta absoluta del XML */
    public String getRutaXML() {
        return obtenerRuta("xml.ruta");
    }

    /** @return ruta absoluta del CSV */
    public String getRutaCSV() {
        return obtenerRuta("csv.ruta");
    }

    /** @return ruta absoluta del JSON del concesionario */
    public String getRutaJSONConcesionario() {
        return obtenerRuta("json.concesionario.ruta");
    }

    /** @return ruta absoluta del JSON de un coche */
    public String getRutaJSONCoche() {
        return obtenerRuta("json.coche.ruta");
    }

    /** @return ruta absoluta del informe */
    public String getRutaInforme() {
        return obtenerRuta("informe.ruta");
    }
}
