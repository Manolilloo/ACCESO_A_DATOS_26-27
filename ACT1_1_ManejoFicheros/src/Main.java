import java.util.Scanner;

/**
 * Interfaz de consola para la gestion de coches.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GestorCoches gestor = new GestorCoches("coches.dat");

    public static void main(String[] args) {
        int op = 0;

        do {
            System.out.println("\n--- MENU COCHES ---");
            System.out.println("1. Cargar CSV");
            System.out.println("2. Insertar coche en posicion");
            System.out.println("3. Ordenar por matricula");
            System.out.println("4. Borrar registro");
            System.out.println("5. Modificar registro");
            System.out.println("6. Listar coches");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(sc.nextLine().trim());
                switch (op) {
                    case 1 -> opcionCargarCsv();
                    case 2 -> opcionInsertar();
                    case 3 -> opcionOrdenar();
                    case 4 -> opcionBorrar();
                    case 5 -> opcionModificar();
                    case 6 -> opcionListar();
                    case 7 -> System.out.println("Fin del programa.");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 7);
    }

    private static void opcionCargarCsv() {
        try {
            System.out.print("Ruta del fichero CSV (ej: BBDD Coches.csv): ");
            String ruta = sc.nextLine().trim();
            gestor.cargarCsv(ruta);
            System.out.println("CSV cargado correctamente. Registros: " + gestor.totalRegistros());
        } catch (Exception e) {
            System.out.println("Fallo al cargar CSV: " + e.getMessage());
        }
    }

    private static void opcionInsertar() {
        try {
            int total = gestor.totalRegistros();
            System.out.print("Posicion donde insertar (0 a " + total + "): ");
            int pos = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Matricula: ");
            String mat = sc.nextLine().trim();
            if (gestor.buscarPosicion(mat) != -1) {
                System.out.println("Error: Ya existe esa matricula.");
                return;
            }

            System.out.print("Marca: ");
            String mar = sc.nextLine().trim();
            System.out.print("Modelo: ");
            String mod = sc.nextLine().trim();

            gestor.insertar(pos, mat, mar, mod);
            System.out.println("Coche insertado en la posicion " + pos + ".");
        } catch (Exception e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    private static void opcionOrdenar() {
        try {
            gestor.ordenarPorMatricula();
            System.out.println("Fichero ordenado por matricula.");
        } catch (Exception e) {
            System.out.println("Error al ordenar: " + e.getMessage());
        }
    }

    private static void opcionBorrar() {
        try {
            System.out.println("1. Borrar por matricula | 2. Borrar por posicion");
            String sub = sc.nextLine().trim();

            if (sub.equals("1")) {
                System.out.print("Matricula: ");
                String mat = sc.nextLine().trim();
                if (gestor.borrarPorMatricula(mat)) {
                    System.out.println("Coche eliminado.");
                } else {
                    System.out.println("No se encontro esa matricula.");
                }
            } else if (sub.equals("2")) {
                System.out.print("Posicion: ");
                int pos = Integer.parseInt(sc.nextLine().trim());
                gestor.borrarPorPosicion(pos);
                System.out.println("Registro en posicion " + pos + " eliminado.");
            } else {
                System.out.println("Opcion no valida.");
            }
        } catch (Exception e) {
            System.out.println("Error al borrar: " + e.getMessage());
        }
    }

    private static void opcionModificar() {
        try {
            System.out.print("Posicion del coche a modificar: ");
            int pos = Integer.parseInt(sc.nextLine().trim());

            String[] actual = gestor.leer(pos);
            System.out.println("Coche actual: " + actual[0] + " - " + actual[1] + " " + actual[2]);

            System.out.print("Nueva Marca: ");
            String nuevaMar = sc.nextLine().trim();
            System.out.print("Nuevo Modelo: ");
            String nuevoMod = sc.nextLine().trim();

            gestor.modificar(pos, nuevaMar, nuevoMod);
            System.out.println("Coche actualizado con exito.");
        } catch (Exception e) {
            System.out.println("Error al modificar: " + e.getMessage());
        }
    }

    private static void opcionListar() {
        try {
            int total = gestor.totalRegistros();
            if (total == 0) {
                System.out.println("El fichero esta vacio.");
                return;
            }
            System.out.println("\nTotal de coches: " + total);
            for (int i = 0; i < total; i++) {
                String[] r = gestor.leer(i);
                System.out.println("[" + i + "] " + r[0] + " | " + r[1] + " | " + r[2]);
            }
        } catch (Exception e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
