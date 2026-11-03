import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Logica para gestionar el fichero binario de coches.
 * Cada registro mide 71 bytes: matricula (7), marca (32), modelo (32).
 */
public class GestorCoches {

    // Medidas fijas en bytes que pide la practica
    private static final int TAM_MAT = 7;
    private static final int TAM_MAR = 32;
    private static final int TAM_MOD = 32;
    private static final int TAM_REG = TAM_MAT + TAM_MAR + TAM_MOD; // 71 bytes en total

    private final String rutaDat;

    public GestorCoches(String rutaDat) {
        this.rutaDat = rutaDat;
    }

    /**
     * Cuenta cuantos coches hay guardados en el archivo dividiendo
     * el tamanyo total en bytes entre lo que ocupa cada coche (71 bytes).
     *
     * @return El numero total de registros existentes.
     * @throws IOException Si hay problemas al acceder al archivo.
     */
    public int totalRegistros() throws IOException {
        File f = new File(rutaDat);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            return (int) (raf.length() / TAM_REG);
        }
    }

    /**
     * Lee un coche completo de una posicion fija del fichero binario.
     * Abre el archivo en modo solo lectura ("r") y coloca el cursor calculando
     * el salto en bytes (posicion * 71 bytes de cada coche). Devuelve un array
     * de String con los tres datos leidos: matricula, marca y modelo.
     *
     * @param pos Numero de posicion del coche que queremos leer (0, 1, 2...).
     * @return Array de 3 Strings: [0] matricula, [1] marca, [2] modelo.
     * @throws IOException Si ocurre algun fallo al abrir o leer el fichero.
     */
    public String[] leer(int pos) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaDat, "r")) {
            raf.seek((long) pos * TAM_REG);
            return new String[] {
                    leerTexto(raf, TAM_MAT),
                    leerTexto(raf, TAM_MAR),
                    leerTexto(raf, TAM_MOD)
            };
        }
    }

    /**
     * Escribe o sobreescribe un coche completo en una posicion dada.
     * Abre el archivo con permisos de lectura y escritura ("rw"), calcula el salto
     * multiplicando la posicion por los 71 bytes que ocupa cada registro para
     * colocarse justo ahi con seek, y va escribiendo en orden sus tres campos:
     * primero la matricula, luego la marca y despues el modelo.
     *
     * @param pos Posicion donde queremos escribir el coche (0, 1, 2...).
     * @param matricula Matricula del coche (hasta 7 caracteres).
     * @param marca Marca del vehiculo (hasta 32 caracteres).
     * @param modelo Modelo del vehiculo (hasta 32 caracteres).
     * @throws IOException Si ocurre algun error al abrir o escribir en el archivo.
     */
    public void escribir(int pos, String matricula, String marca, String modelo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaDat, "rw")) {
            raf.seek((long) pos * TAM_REG);
            escribirTexto(raf, matricula, TAM_MAT);
            escribirTexto(raf, marca, TAM_MAR);
            escribirTexto(raf, modelo, TAM_MOD);
        }
    }

    /**
     * Busca en que posicion esta guardado un coche a partir de su matricula.
     * Cuenta cuantos registros hay en total y los va mirando uno a uno con un bucle.
     * Lee los datos de cada coche y compara la matricula (limpia y sin fijarse
     * en mayusculas o minusculas) con la que le hemos pasado. Si coincide devuelve
     * la posicion en la que esta, y si termina de revisar todos y no lo encuentra,
     * devuelve -1.
     *
     * @param matricula La matricula que queremos localizar en el fichero.
     * @return El numero de posicion del coche (0, 1, 2...) o -1 si no existe.
     * @throws IOException Si ocurre algun fallo al leer el archivo.
     */
    public int buscarPosicion(String matricula) throws IOException {
        int total = totalRegistros();
        for (int i = 0; i < total; i++) {
            String[] datos = leer(i);
            if (datos[0].equalsIgnoreCase(matricula.trim())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Carga todos los coches de un archivo CSV en nuestro fichero binario de datos.
     * Si el CSV no existe, suelta un error. Borra la base de datos vieja si ya existia
     * para empezar limpios. Va leyendo el texto linea a linea, saltandose las lineas
     * vacias y la cabecera con los titulos. Trocea cada linea para sacar la matricula,
     * marca y modelo, y si la matricula ya esta repetida en el archivo no la guarda.
     * Si es nueva, la mete al final de la fila.
     *
     * @param rutaCsv Ruta o nombre del archivo CSV que queremos importar.
     * @throws IOException Si el archivo no existe o hay problemas al leerlo/escribirlo.
     */
    public void cargarCsv(String rutaCsv) throws IOException {
        File fCsv = new File(rutaCsv);
        if (!fCsv.exists()) {
            throw new IOException("El archivo CSV no existe.");
        }

        // Me cargo el fichero viejo si existia para no juntar datos
        new File(rutaDat).delete();

        try (BufferedReader br = new BufferedReader(new FileReader(fCsv))) {
            String linea;
            boolean cabecera = true;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Me salto los titulos de las columnas de la primera linea
                if (cabecera) {
                    cabecera = false;
                    if (linea.toLowerCase().contains("matr")) continue;
                }

                String sep = linea.contains(";") ? ";" : ",";
                String[] partes = linea.split(sep);
                if (partes.length < 3) continue;

                String mat = partes[0].trim();
                String mar = partes[1].trim();
                String mod = partes[2].trim();

                // Si la matricula ya esta en el archivo, paso para no duplicar
                if (buscarPosicion(mat) != -1) continue;

                escribir(totalRegistros(), mat, mar, mod);
            }
        }
    }

    /**
     * Inserta un coche nuevo en la posicion que le digamos.
     * Si la posicion no tiene sentido (menor que 0 o mas alla del final), lanza una excepcion.
     * Para no pisar nada, va desde el final hacia atras copiando cada coche una posicion
     * a la derecha para dejar el hueco libre. Una vez hecho el sitio, escribe el coche
     * nuevo en esa posicion.
     *
     * @param pos Posicion donde queremos meter el coche (0, 1, 2... hasta el total para meterlo al final).
     * @param matricula Clave del coche (maximo 7 caracteres).
     * @param marca Marca del coche (maximo 32 caracteres).
     * @param modelo Modelo del coche (maximo 32 caracteres).
     * @throws IOException Si ocurre algun fallo al leer o escribir en el fichero.
     * @throws IllegalArgumentException Si nos pasan una posicion fuera de los limites.
     */
    public void insertar(int pos, String matricula, String marca, String modelo) throws IOException {
        int total = totalRegistros();
        if (pos < 0 || pos > total) {
            throw new IllegalArgumentException("Posicion fuera de rango.");
        }

        // Muevo los coches hacia la derecha desde el final para abrir el hueco
        for (int i = total - 1; i >= pos; i--) {
            String[] r = leer(i);
            escribir(i + 1, r[0], r[1], r[2]);
        }

        escribir(pos, matricula, marca, modelo);
    }

    /**
     * Modifica la marca y el modelo de un coche en una posicion dada.
     * La matricula no se toca para cumplir con las normas de la practica.
     * Mira que la posicion exista dentro del archivo y luego mueve el puntero
     * hasta ese registro, saltandose los 7 bytes de la matricula para sobreescribir
     * directamente la marca y el modelo nuevos.
     *
     * @param pos Posicion del coche que queremos cambiar (0, 1, 2...).
     * @param nuevaMarca El nuevo texto de la marca (maximo 32 caracteres).
     * @param nuevoModelo El nuevo texto del modelo (maximo 32 caracteres).
     * @throws IOException Si ocurre algun problema al abrir o escribir en el archivo.
     * @throws IllegalArgumentException Si nos pasan una posicion que no existe.
     */
    public void modificar(int pos, String nuevaMarca, String nuevoModelo) throws IOException {
        int total = totalRegistros();
        if (pos < 0 || pos >= total) {
            throw new IllegalArgumentException("Posicion invalida.");
        }

        try (RandomAccessFile raf = new RandomAccessFile(rutaDat, "rw")) {
            // Me coloco en el coche que toca y me salto la matricula para no tocarla
            raf.seek(((long) pos * TAM_REG) + TAM_MAT);
            escribirTexto(raf, nuevaMarca, TAM_MAR);
            escribirTexto(raf, nuevoModelo, TAM_MOD);
        }
    }

    /**
     * Borra un coche segun el numero de posicion que le pasemos.
     * Primero mira que la posicion exista en el fichero para no liarla.
     * Luego va corriendo todos los coches siguientes una posicion a la izquierda
     * para sobreescribir el que queremos quitar, y al final le mete un recorte
     * al archivo con setLength para quitar el ultimo que se queda repetido.
     *
     * @param pos Indice del coche que queremos cargarle (0, 1, 2...).
     * @throws IOException Si da fallo al leer o recortar el fichero.
     * @throws IllegalArgumentException Si nos pasan una posicion que no existe.
     */
    public void borrarPorPosicion(int pos) throws IOException {
        int total = totalRegistros();
        if (pos < 0 || pos >= total) {
            throw new IllegalArgumentException("Posicion invalida.");
        }

        // Voy pasando cada coche a la casilla anterior para pisar el que borro
        for (int i = pos; i < total - 1; i++) {
            String[] r = leer(i + 1);
            escribir(i, r[0], r[1], r[2]);
        }

        // Corto el archivo para que pese un registro menos y no quede el ultimo duplicado
        try (RandomAccessFile raf = new RandomAccessFile(rutaDat, "rw")) {
            raf.setLength((long) (total - 1) * TAM_REG);
        }
    }

    /**
     * Borra un coche buscando primero por su matricula.
     * Si no la encuentra devuelve false, y si la encuentra llama al metodo
     * borrarPorPosicion para eliminarlo del archivo y devuelve true.
     *
     * @param matricula Matricula del coche a eliminar.
     * @return true si se borro con exito, false si no se encontro.
     * @throws IOException Si falla al leer o modificar el archivo.
     */
    public boolean borrarPorMatricula(String matricula) throws IOException {
        int pos = buscarPosicion(matricula);
        if (pos == -1) return false;
        borrarPorPosicion(pos);
        return true;
    }

    /**
     * Ordena todo el fichero de coches por el campo matricula de la A a la Z.
     * Va comparando en parejas con dos bucles: lee el coche de la posicion actual
     * y lo compara con los que vienen detras. Si la matricula del primer coche es mayor
     * en el abecedario que la del segundo, intercambia las posiciones de ambos coches
     * en el archivo escribiendo uno en el sitio del otro.
     *
     * @throws IOException Si ocurre un error al leer o escribir registros en el archivo.
     */
    public void ordenarPorMatricula() throws IOException {
        int total = totalRegistros();
        for (int i = 0; i < total - 1; i++) {
            for (int j = i + 1; j < total; j++) {
                String[] coche1 = leer(i);
                String[] coche2 = leer(j);

                if (coche1[0].compareToIgnoreCase(coche2[0]) > 0) {
                    escribir(i, coche2[0], coche2[1], coche2[2]);
                    escribir(j, coche1[0], coche1[1], coche1[2]);
                }
            }
        }
    }

    //Estos son simplemente métodos de apoyo para empaquetar y desempaquetar bytes fijos

    // Escribe un texto asegurando que ocupe exactamente 'limite' bytes (rellena con espacios)
    private void escribirTexto(RandomAccessFile raf, String texto, int limite) throws IOException {
        if (texto == null) texto = "";
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        byte[] salida = new byte[limite];

        // Lleno con espacios en blanco
        for (int i = 0; i < limite; i++) {
            salida[i] = (byte) ' ';
        }
        // Copio los bytes que quepan
        System.arraycopy(bytes, 0, salida, 0, Math.min(bytes.length, limite));
        raf.write(salida);
    }

    // Lee una cantidad fija de bytes y quita los espacios sobrantes del final
    private String leerTexto(RandomAccessFile raf, int limite) throws IOException {
        byte[] b = new byte[limite];
        raf.readFully(b);
        return new String(b, StandardCharsets.UTF_8).trim();
    }
}
