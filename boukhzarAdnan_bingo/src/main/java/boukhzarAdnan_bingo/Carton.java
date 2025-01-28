package boukhzarAdnan_bingo;

import java.util.Random;

public class Carton {
    private String[][] numeros; // Números del cartón
    private boolean lineaCantada; // Indica si ya se cantó línea en este cartón
    private final int FILAS = 3;
    private final int COLUMNAS = 9;

    // Constructor
    public Carton() {
        numeros = new String[FILAS][COLUMNAS];
        generarCarton();
        lineaCantada = false;
    }

    // Genera un cartón con números únicos y posiciones vacías
    private void generarCarton() {
        int[] registro = new int[91];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                numeros[i][j] = String.valueOf(utilitats.RandomNoRepetit(registro, 90));
            }
            // Asignar posiciones vacías ("@") aleatoriamente en cada fila
            int[] posicionesVacias = utilitats.posicionesUnicas(COLUMNAS, 4);
            for (int posicion : posicionesVacias) {
                numeros[i][posicion] = "@";
            }
        }
    }

    // Marca un número en el cartón si existe
    public boolean marcarNumero(int numero) {
        boolean marcado = false;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (numeros[i][j].equals(String.valueOf(numero))) {
                    numeros[i][j] = "X"; // Marca el número
                    marcado = true;
                }
            }
        }
        return marcado;
    }

    // Comprueba si el cartón tiene línea
    public boolean tieneLinea() {
        for (int i = 0; i < FILAS; i++) {
            int contador = 0;
            for (int j = 0; j < COLUMNAS; j++) {
                if (numeros[i][j].equals("X")) {
                    contador++;
                }
            }
            if (contador == 5 && !lineaCantada) {
                lineaCantada = true;
                return true;
            }
        }
        return false;
    }

    // Comprueba si el cartón tiene bingo
    public boolean tieneBingo() {
        int contador = 0;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (numeros[i][j].equals("X")) {
                    contador++;
                }
            }
        }
        return contador == 15; // Bingo si se han marcado los 15 números
    }

    // Muestra el cartón
    public void mostrarCarton() {
        System.out.println("CARTÓN:");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.printf("%4s", numeros[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}


