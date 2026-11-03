package principal;

import config.Configuracion;
import model.Coche;
import model.Concesionario;
import service.ConcesionarioService;
import service.CsvService;
import service.JsonService;
import service.XmlService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal de la aplicación.
 */
public class Main {

    /**
     * Inicia la aplicación y muestra el menú principal.
     *
     * @param args argumentos recibidos al ejecutar el programa
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        try {
            Configuracion configuracion = new Configuracion();

            XmlService xmlService = new XmlService(configuracion);
            Concesionario concesionario = xmlService.cargarConcesionario();

            ConcesionarioService servicio =
                    new ConcesionarioService(concesionario, xmlService);

            CsvService csvService = new CsvService(configuracion, servicio);
            JsonService jsonService = new JsonService(configuracion);

            System.out.println("Concesionario cargado correctamente.");

            do {
                System.out.println("\n===== GESTIÓN DEL CONCESIONARIO =====");
                System.out.println("1. Importar coches desde CSV");
                System.out.println("2. Insertar un coche nuevo");
                System.out.println("3. Ordenar coches por matrícula");
                System.out.println("4. Borrar un coche por matrícula");
                System.out.println("5. Modificar un coche por matrícula");
                System.out.println("6. Exportar a JSON");
                System.out.println("7. Importar desde JSON");
                System.out.println("8. Generar informe resumen");
                System.out.println("0. Salir");
                System.out.print("Selecciona una opción: ");

                opcion = -1;

                try {
                    opcion = Integer.parseInt(sc.nextLine().trim());

                    switch (opcion) {

                        case 1:
                            int cantidad = csvService.importarCSV();
                            System.out.println("Coches importados: " + cantidad);
                            break;

                        case 2:
                            insertarCoche(sc, servicio);
                            break;

                        case 3:
                            servicio.ordenarPorMatricula();
                            System.out.println("Coches ordenados correctamente.");
                            break;

                        case 4:
                            borrarCoche(sc, servicio);
                            break;

                        case 5:
                            modificarCoche(sc, servicio);
                            break;

                        case 6:
                            exportarJSON(sc, jsonService, concesionario, servicio);
                            break;

                        case 7:
                            importarJSON(sc, jsonService, servicio);
                            break;

                        case 8:
                            System.out.println("Esta opción todavía no está conectada.");
                            break;

                        case 0:
                            System.out.println("Saliendo del programa...");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Introduce un número válido.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println("No se pudo iniciar el programa: " + e.getMessage());
        }

        sc.close();
    }

    /**
     * Pide los datos e inserta un coche nuevo.
     */
    private static void insertarCoche(Scanner sc, ConcesionarioService servicio) throws Exception {

        System.out.print("Matrícula: ");
        String matricula = sc.nextLine().trim();

        System.out.print("Marca: ");
        String marca = sc.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine().trim();

        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            System.out.println("Debes rellenar todos los datos.");
            return;
        }

        System.out.print("Extras separados por |: ");
        String textoExtras = sc.nextLine();

        List<String> extras = leerExtras(textoExtras);

        Coche coche = new Coche(0, matricula, marca, modelo, extras);

        if (servicio.insertarCoche(coche)) {
            System.out.println("Coche insertado correctamente.");
        } else {
            System.out.println("Ya existe un coche con esa matrícula.");
        }
    }

    /**
     * Busca y borra un coche por su matrícula.
     */
    private static void borrarCoche(Scanner sc, ConcesionarioService servicio) throws Exception {

        System.out.print("Matrícula del coche que quieres borrar: ");
        String matricula = sc.nextLine().trim();

        if (servicio.borrarCoche(matricula)) {
            System.out.println("Coche eliminado correctamente.");
        } else {
            System.out.println("No se ha encontrado esa matrícula.");
        }
    }

    /**
     * Modifica la marca, el modelo y los extras de un coche.
     */
    private static void modificarCoche(Scanner sc, ConcesionarioService servicio) throws Exception {

        System.out.print("Matrícula del coche que quieres modificar: ");
        String matricula = sc.nextLine().trim();

        Coche coche = servicio.buscarCoche(matricula);

        if (coche == null) {
            System.out.println("No se ha encontrado ese coche.");
            return;
        }

        System.out.println("Coche actual: " + coche.getMarca() + " " + coche.getModelo());

        System.out.print("Nueva marca: ");
        String marca = sc.nextLine().trim();

        System.out.print("Nuevo modelo: ");
        String modelo = sc.nextLine().trim();

        if (marca.isEmpty() || modelo.isEmpty()) {
            System.out.println("La marca y el modelo no pueden estar vacíos.");
            return;
        }

        System.out.println("Nuevos extras separados por |.");
        System.out.println("Deja vacío para mantenerlos o escribe - para eliminarlos.");
        System.out.print("Extras: ");

        String textoExtras = sc.nextLine().trim();

        List<String> extras;

        if (textoExtras.isEmpty()) {
            extras = coche.getExtrasCoche();
        } else if (textoExtras.equals("-")) {
            extras = new ArrayList<>();
        } else {
            extras = leerExtras(textoExtras);
        }

        servicio.modificarCoche(matricula, marca, modelo, extras);

        System.out.println("Coche modificado correctamente.");
    }

    /**
     * Separa los extras introducidos por el usuario.
     */
    private static List<String> leerExtras(String texto) {

        List<String> extras = new ArrayList<>();

        if (!texto.trim().isEmpty()) {
            String[] lista = texto.split("\\|");

            for (String extra : lista) {
                if (!extra.trim().isEmpty()) {
                    extras.add(extra.trim());
                }
            }
        }

        return extras;
    }

    /**
     * Muestra las opciones para exportar datos a JSON.
     */
    private static void exportarJSON(Scanner sc, JsonService jsonService,
                                     Concesionario concesionario,
                                     ConcesionarioService servicio) throws Exception {

        System.out.println("1. Exportar concesionario completo");
        System.out.println("2. Exportar un coche por matrícula");
        System.out.print("Selecciona una opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {

            case 1:
                jsonService.exportarConcesionario(concesionario);
                System.out.println("Concesionario exportado correctamente.");
                break;

            case 2:
                System.out.print("Matrícula del coche: ");
                String matricula = sc.nextLine().trim();

                Coche coche = servicio.buscarCoche(matricula);

                if (coche == null) {
                    System.out.println("No se ha encontrado ese coche.");
                } else {
                    jsonService.exportarCoche(coche);
                    System.out.println("Coche exportado correctamente.");
                }
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

    /**
     * Muestra las opciones para importar datos desde JSON.
     */
    private static void importarJSON(Scanner sc, JsonService jsonService,
                                     ConcesionarioService servicio) throws Exception {

        System.out.println("1. Importar concesionario completo");
        System.out.println("2. Importar un coche");
        System.out.print("Selecciona una opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {

            case 1:
                Concesionario nuevo = jsonService.importarConcesionario();

                if (nuevo == null) {
                    System.out.println("El archivo JSON está vacío.");
                } else {
                    servicio.reemplazarConcesionario(nuevo);
                    System.out.println("Concesionario importado correctamente.");
                }
                break;

            case 2:
                Coche coche = jsonService.importarCoche();

                if (coche == null) {
                    System.out.println("No se ha encontrado ningún coche en el JSON.");
                } else if (servicio.insertarCoche(coche)) {
                    System.out.println("Coche importado correctamente.");
                } else {
                    System.out.println("Ya existe un coche con esa matrícula.");
                }
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

}
