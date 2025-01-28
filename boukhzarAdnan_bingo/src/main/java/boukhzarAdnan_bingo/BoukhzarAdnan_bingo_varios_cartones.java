package boukhzarAdnan_bingo;

import java.util.ArrayList;
import java.util.List;

public class BoukhzarAdnan_bingo_varios_cartones {

    public static void main(String[] args) {
        utilitats.iniciarJuego();

        // Inicializar bombo
        int[] bombo = new int[91];
        utilitats.inicializarBombo(bombo);

        // Crear lista de cartones
        List<Carton> cartones = new ArrayList<>();
        int numCartones = utilitats.llegirInt("¿Cuántos cartones quieres jugar? ");
        for (int i = 0; i < numCartones; i++) {
            cartones.add(new Carton());
        }

        // Variables de control
        boolean continuar = true;
        int[] contador = {0};
        boolean bingo = false;

        while (continuar && !bingo) {
            // Extraer número del bombo
            int numeroExtraido = utilitats.extraerNumero(bombo, contador, 90, continuar);
            System.out.println("Número extraído: " + numeroExtraido);

            // Marcar número en todos los cartones
            for (int i = 0; i < cartones.size(); i++) {
                Carton carton = cartones.get(i);
                if (carton.marcarNumero(numeroExtraido)) {
                    System.out.println("¡Número encontrado en el cartón " + (i + 1) + "!");
                }

                // Comprobar línea
                if (carton.tieneLinea()) {
                    System.out.println("¡Línea en el cartón " + (i + 1) + "!");
                }

                // Comprobar bingo
                if (carton.tieneBingo()) {
                    System.out.println("¡BINGO en el cartón " + (i + 1) + "!");
                    bingo = true;
                }
            }

            // Mostrar todos los cartones
            for (int i = 0; i < cartones.size(); i++) {
                System.out.println("Cartón " + (i + 1) + ":");
                cartones.get(i).mostrarCarton();
            }

            // Preguntar si continuar
            if (!bingo) {
                continuar = utilitats.llegirInt("¿Quieres continuar? (1: Sí, 0: No) ") == 1;
            }
        }

        // Mostrar números extraídos
        utilitats.mostrarBombo(bombo);
        System.out.println("Gracias por jugar al Bingo!");
    }
}
