import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class GestorCoches {

    // Constantes con los tamaños de cada campo en bytes
    private static final int BYTES_MATRICULA = 7;
    private static final int BYTES_MARCA = 32;
    private static final int BYTES_MODELO = 32;

    // Tamaño total de un registro completo
    private static final int LONGITUD_REGISTRO = BYTES_MATRICULA + BYTES_MARCA + BYTES_MODELO; // 71 bytes

    // Byte de relleno para completar los huecos libres
    private static final byte BYTE_ESPACIO = (byte) ' ';

    // Nombre del fichero binario que hará de base de datos
    private static final String FICHERO_DATOS = "coches.dat";

    public static byte[] formatearABytes(String texto, int tamanoMaximo) {
        if (texto == null) {
            texto = "";
        }
        byte[] bytesTexto = texto.getBytes(StandardCharsets.UTF_8);

        if (bytesTexto.length > tamanoMaximo) {
            throw new IllegalArgumentException("Tamaño maximo invalido");
        }
        byte[] resultado = new byte[tamanoMaximo];
        Arrays.fill(resultado, BYTE_ESPACIO);
        System.arraycopy(texto.getBytes(), 0, resultado, 0, tamanoMaximo);
        return resultado;
    }
}