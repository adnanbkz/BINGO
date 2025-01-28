/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package boukhzarAdnan_bingo;
import java.util.Arrays;

import static boukhzarAdnan_bingo.utilitats.inicializarBombo;

/**
 *
 * @author adnan
 */
public class BoukhzarAdnan_bingo_un_carton {

    public static void main(String[] args) {
        String[][] carton = new String[3][9];

        //INICIALIZAR JUEGO
        utilitats.iniciarJuego();
        
        //GENERAR CARTON
        utilitats.generarCarton(carton);

        //MOSTRAR CARTON
        //utilitats.mostrarCarton(carton);

        //INICIALIZAR BOMBO 
        int[] bombo = new int[91];
        inicializarBombo(bombo);
        int[] registroNumsAciertos = new int[15];
        int [] registroNumerosBombo = new int[90];

        //JUGARº
        boolean continuar = true; 
        int[] contador = {0}; // Array de tamaño 1 para el contador
        int[] contadorAciertos = {0}; // Array de tamaño 1 para el contador de aciertos
        // La razon por la que uso arrays unidimensionales de tamaño 1 es porque no se pueden modificar variables primitivas en un metodo.
        // Esto quiere decir que, si yo usara un contador normal, no se me actualizaria el valor en el metodo jugar, sino que me cojería el ultimo valor que se le asignó.
        // ESto son notas para mí, no las tengas en cuenta Jordi, por si estas leyendo esto.

        boolean bingo = false;
        int contadorPartida = 0;
        System.out.println();
        do {
            utilitats.jugar(carton, bombo, contador,contadorAciertos, continuar, registroNumsAciertos, registroNumerosBombo);
            contadorPartida++;

        } while ((!continuar && !bingo) && contadorPartida < 90);

        
        System.out.println();
        System.out.println();

        

        // Imprimir el cartón final
        utilitats.mostrarCarton(carton);        
        // Imprimir el bombo para ver que numeros han salido
        
        //En caso de que querramos ver el bombo ordenado:
        //Arrays.sort(bombo);
        utilitats.mostrarBombo(bombo);


    }

}
