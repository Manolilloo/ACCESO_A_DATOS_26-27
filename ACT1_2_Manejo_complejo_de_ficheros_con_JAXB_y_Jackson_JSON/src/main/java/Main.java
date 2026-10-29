import java.util.Scanner;

/**
 * @author ManuelPerez
 * @version 1.0
 */

public class Main {
    static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DEL CONCESIONARIO =====");
            System.out.println("1. Importar coches desde CSV");
            System.out.println("2. Insertar un coche nuevo");
            System.out.println("3. Ordenar coches por matrícula");
            System.out.println("4. Borrar un coche por matrícula");
            System.out.println("5. Modificar un coche por matrícula");
            System.out.println("6. Exportar a JSON");
            System.out.println("   1. Exportar concesionario completo");
            System.out.println("   2. Exportar un coche por matrícula");
            System.out.println("7. Importar desde JSON");
            System.out.println("   1. Importar concesionario completo");
            System.out.println("   2. Importar un coche");
            System.out.println("8. Generar informe resumen");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    // Importar coches desde CSV
                    break;
                case 2:
                    // Insertar un coche nuevo
                    break;
                case 3:
                    // Ordenar coches por matrícula
                    break;
                case 4:
                    // Borrar un coche por matrícula
                    break;
                case 5:
                    // Modificar un coche por matrícula
                    break;
                case 6:
                    System.out.println("1. Exportar concesionario completo");
                    System.out.println("2. Exportar un coche por matrícula");
                    int exportacion = sc.nextInt();
                    sc.nextLine();

                    switch (exportacion) {
                        case 1:
                            // Exportar concesionario a JSON
                            break;
                        case 2:
                            // Buscar coche por matrícula y exportarlo a JSON
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;
                case 7:
                    System.out.println("1. Importar concesionario completo");
                    System.out.println("2. Importar un coche");
                    int importacion = sc.nextInt();
                    sc.nextLine();

                    switch (importacion) {
                        case 1:
                            // Importar concesionario desde JSON y actualizar XML
                            break;
                        case 2:
                            // Importar un coche desde JSON y añadirlo al concesionario
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;
                case 8:
                    // Generar informe_concesionario.txt
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
