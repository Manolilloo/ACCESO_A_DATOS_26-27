package service;

import config.Configuracion;
import model.Coche;
import model.Concesionario;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Se encarga de importar y exportar datos en formato JSON mediante Jackson.
 */
public class JsonService {

    private Configuracion configuracion;
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Crea el servicio JSON con la configuración de las rutas.
     *
     * @param configuracion configuración de los archivos
     */
    public JsonService(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Exporta todo el concesionario a un archivo JSON.
     *
     * @param concesionario concesionario que se quiere exportar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public void exportarConcesionario(Concesionario concesionario) throws IOException {

        File archivo = new File(configuracion.getRutaJSONConcesionario());

        mapper.writeValue(archivo, concesionario);
    }

    /**
     * Exporta un coche individual a un archivo JSON.
     *
     * @param coche coche que se quiere exportar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public void exportarCoche(Coche coche) throws IOException {

        File archivo = new File(configuracion.getRutaJSONCoche());

        mapper.writeValue(archivo, coche);
    }

    /**
     * Importa un concesionario completo desde un archivo JSON.
     *
     * @return concesionario leído del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public Concesionario importarConcesionario() throws IOException {

        File archivo = new File(configuracion.getRutaJSONConcesionario());

        return mapper.readValue(archivo, Concesionario.class);
    }

    /**
     * Importa un coche individual desde un archivo JSON.
     *
     * @return coche leído del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public Coche importarCoche() throws IOException {

        File archivo = new File(configuracion.getRutaJSONCoche());

        return mapper.readValue(archivo, Coche.class);
    }
}
