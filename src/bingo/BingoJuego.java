package bingo;

import java.util.*;

public class BingoJuego {

    private final BingoCarton carton;
    private final Set<Integer> numerosSacados;
    private final Random random;

    public BingoJuego(BingoCarton carton) {
        this.carton = carton;
        this.numerosSacados = new HashSet<>();
        this.random = new Random();
    }

    public int sacarNumero() {
        if (numerosSacados.size() >= 90) {
            return -1; // Evita que se sigan sacando números si ya se sortearon todos
        }

        int numero;
        do {
            numero = random.nextInt(90) + 1;
        } while (numerosSacados.contains(numero));

        numerosSacados.add(numero);
        carton.marcarNumero(numero);
        return numero;
    }

    public BingoCarton getCarton() {
        return carton;
    }

}
