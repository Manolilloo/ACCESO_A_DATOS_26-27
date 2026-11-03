package service;

import config.Configuracion;
import model.Coche;
import model.Concesionario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de generar el informe resumen del concesionario.
 */
public class InformeService {

    private Configuracion configuracion;

    /**
     * Crea el servicio con la configuración de las rutas.
     *
     * @param configuracion configuración de los archivos
     */
    public InformeService(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Genera un informe con los datos del concesionario.
     *
     * @param concesionario concesionario del que se obtiene la información
     * @throws IOException si ocurre un error al escribir el informe
     */
    public void generarInforme(Concesionario concesionario) throws IOException {

        File archivo = new File(configuracion.getRutaInforme());

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(archivo, StandardCharsets.UTF_8))) {

            List<Coche> coches = concesionario.getCoches();

            escritor.write("INFORME DEL CONCESIONARIO");
            escritor.newLine();
            escritor.write("-------------------------");
            escritor.newLine();

            // Número total de coches
            escritor.write("Total de coches: " + (coches.size()));
            escritor.newLine();
            escritor.newLine();

            // Agrupar los coches por marca
            escritor.write("COCHES AGRUPADOS POR MARCA");
            escritor.newLine();
            escritor.write("-------------------------");
            escritor.newLine();

            List<String> marcasProcesadas = new ArrayList<>();

            for (Coche coche : coches) {

                String marca = coche.getMarca();

                if (!marcasProcesadas.contains(marca)) {

                    escritor.newLine();
                    escritor.write("Marca: " + marca);
                    escritor.newLine();

                    for (Coche otroCoche : coches) {
                        if (otroCoche.getMarca().equalsIgnoreCase(marca)) {
                            escritor.write("- " + otroCoche.getMatriculaCoche()
                                    + " | " + otroCoche.getModelo());
                            escritor.newLine();
                        }
                    }

                    marcasProcesadas.add(marca);
                }
            }

            // Buscar el extra que más veces aparece
            String extraMasRepetido = "";
            int maxRepeticiones = 0;

            for (Coche coche : coches) {
                for (String extra : coche.getExtrasCoche()) {

                    int repeticiones = 0;

                    for (Coche otroCoche : coches) {
                        for (String otroExtra : otroCoche.getExtrasCoche()) {

                            if (extra.equalsIgnoreCase(otroExtra)) {
                                repeticiones++;
                            }
                        }
                    }

                    if (repeticiones > maxRepeticiones) {
                        maxRepeticiones = repeticiones;
                        extraMasRepetido = extra;
                    }
                }
            }

            escritor.newLine();
            escritor.write("EXTRA MÁS REPETIDO");
            escritor.newLine();
            escritor.write("------------------");

            if (maxRepeticiones > 0) {
                escritor.newLine();
                escritor.write(extraMasRepetido + ": " + maxRepeticiones + " veces");
            } else {
                escritor.newLine();
                escritor.write("No hay extras registrados.");
            }
        }
    }
}
