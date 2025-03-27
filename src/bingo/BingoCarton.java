package bingo;

import java.util.*;

public class BingoCarton {

    private final int[][] carton;
    private final boolean[][] marcados;
    private boolean lineaAnunciada = false; // Solo anuncia la primera línea una vez

    public BingoCarton() {
        carton = new int[3][9];
        marcados = new boolean[3][9];
        generarCarton();
    }

    private void generarCarton() {
        Random random = new Random();
        List<List<Integer>> columnas = new ArrayList<>();

        // Inicializar listas para cada columna
        for (int i = 0; i < 9; i++) {
            columnas.add(new ArrayList<>());
        }

        // Generar los números de cada columna dentro del rango correcto
        for (int col = 0; col < 9; col++) {
            int min = col * 10 + 1;
            int max = (col == 8) ? 90 : (col + 1) * 10;

            // Seleccionamos 3 números únicos por columna
            Set<Integer> numeros = new TreeSet<>();
            while (numeros.size() < 3) {
                numeros.add(random.nextInt(max - min + 1) + min);
            }

            columnas.get(col).addAll(numeros); // Guardamos los números ordenados
        }

        // Asignar los números al cartón asegurando que cada fila tenga 5 números
        for (int fila = 0; fila < 3; fila++) {
            List<Integer> indicesSeleccionados = new ArrayList<>();

            // Elegir 5 columnas aleatorias para colocar números en esta fila
            while (indicesSeleccionados.size() < 5) {
                int col = random.nextInt(9);
                if (!indicesSeleccionados.contains(col)) {
                    indicesSeleccionados.add(col);
                }
            }

            // Colocar los números en las columnas seleccionadas
            for (int col = 0; col < 9; col++) {
                if (indicesSeleccionados.contains(col)) {
                    carton[fila][col] = columnas.get(col).remove(0); // Extraemos en orden
                } else {
                    carton[fila][col] = 0; // Espacios en blanco
                }
            }
        }
    }

    public void marcarNumero(int numero) {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (carton[fila][col] == numero) {
                    marcados[fila][col] = true;
                }
            }
        }
    }

    public boolean verificarPrimeraLinea() {
        if (this.lineaAnunciada) {
            return false; // Si ya anunciamos una línea, no repetir
        }
        for (int fila = 0; fila < 3; fila++) {
            boolean lineaCompleta = true;
            for (int col = 0; col < 9; col++) {
                if (carton[fila][col] != 0 && !marcados[fila][col]) {
                    lineaCompleta = false;
                }
            }
            if (lineaCompleta) {
                this.lineaAnunciada = true; // Marcamos que ya se anunció una línea
                return true; // Retorna true solo la primera vez
            }

        }
        return false;
    }

    public boolean verificarBingo() {
        int filasCompletas = 0;
        for (int fila = 0; fila < 3; fila++) {
            boolean lineaCompleta = true;
            for (int col = 0; col < 9; col++) {
                if (carton[fila][col] != 0 && !marcados[fila][col]) {
                    lineaCompleta = false;
                }
            }
            if (lineaCompleta) {
                filasCompletas++;
            }
        }
        return filasCompletas == 3; // Cambia a 1 si quieres ganar con solo una línea
    }

    public int[][] getCarton() {
        return carton;
    }

    public boolean[][] getMarcados() {
        return marcados;
    }
}
