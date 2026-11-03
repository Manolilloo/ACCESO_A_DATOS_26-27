package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Permite cargar las rutas de los archivos desde config.properties.
 */
public class Configuracion {

    private Properties propiedades = new Properties();

    /**
     * Carga el archivo de configuración.
     *
     * @throws IOException si ocurre un error al leer el archivo
     */
    public Configuracion() throws IOException {

        try (InputStream archivo = getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (archivo == null) {
                throw new FileNotFoundException("No se ha encontrado config.properties");
            }

            propiedades.load(archivo);
        }
    }

    /** Devuelve la ruta del XML. */
    public String getRutaXML() {
        return propiedades.getProperty("xml.ruta");
    }

    /** Devuelve la ruta del CSV. */
    public String getRutaCSV() {
        return propiedades.getProperty("csv.ruta");
    }

    /** Devuelve la ruta del JSON del concesionario. */
    public String getRutaJSONConcesionario() {
        return propiedades.getProperty("json.concesionario.ruta");
    }

    /** Devuelve la ruta del JSON de un coche. */
    public String getRutaJSONCoche() {
        return propiedades.getProperty("json.coche.ruta");
    }

    /** Devuelve la ruta del informe. */
    public String getRutaInforme() {
        return propiedades.getProperty("informe.ruta");
    }
}
