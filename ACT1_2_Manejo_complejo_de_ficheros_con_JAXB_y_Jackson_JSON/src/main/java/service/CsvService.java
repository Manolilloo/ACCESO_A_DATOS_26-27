package service;

import config.Configuracion;
import model.Coche;

import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de importar coches desde archivos CSV.
 */
public class CsvService {

    private Configuracion configuracion;
    private ConcesionarioService concesionarioService;

    /**
     * Crea el servicio de importación CSV.
     *
     * @param configuracion configuración de las rutas
     * @param concesionarioService servicio para gestionar los coches
     */
    public CsvService(Configuracion configuracion, ConcesionarioService concesionarioService) {
        this.configuracion = configuracion;
        this.concesionarioService = concesionarioService;
    }

    /**
     * Importa los coches del archivo CSV al concesionario.
     *
     * @return número de coches importados
     * @throws IOException si ocurre un error al leer el archivo
     * @throws JAXBException si ocurre un error al guardar el XML
     */
    public int importarCSV() throws IOException, JAXBException {

        int cochesImportados = 0;
        boolean cabecera = true;

        try (BufferedReader br = new BufferedReader(
                new FileReader(configuracion.getRutaCSV(), StandardCharsets.UTF_8))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                if (cabecera) {
                    cabecera = false;
                    continue;
                }

                String[] datos = linea.split(";", -1);

                if (datos.length != 4) {
                    throw new IOException("Hay una línea del CSV con datos incorrectos.");
                }

                String matricula = datos[0].trim();
                String marca = datos[1].trim();
                String modelo = datos[2].trim();

                if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                    throw new IOException("Faltan datos en una línea del CSV.");
                }

                List<String> extras = new ArrayList<>();

                if (!datos[3].trim().isEmpty()) {
                    String[] listaExtras = datos[3].split("\\|");

                    for (String extra : listaExtras) {
                        extras.add(extra.trim());
                    }
                }

                Coche coche = new Coche(0, matricula, marca, modelo, extras);

                if (concesionarioService.insertarCoche(coche)) {
                    cochesImportados++;
                }
            }
        }

        return cochesImportados;
    }
}
