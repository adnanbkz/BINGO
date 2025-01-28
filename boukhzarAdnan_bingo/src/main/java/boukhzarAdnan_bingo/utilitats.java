package boukhzarAdnan_bingo;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author adnan
 */
public class utilitats {

    // 1. CONFIGURACION Y INICIALIZACION

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
    }
    
    // 1. CONFIGURACION Y INICIALIZACION
    /**
     * Se inicia el juego, mostrando el banner del bingo
     */
    public static void iniciarJuego() {
        System.out.println("BIENVENIDO AL JUEGO DEL BINGO");
        mostrarBannerBingo();
    }

    /**
     * METODO PRINCIPAL DEL JUEGO, DONDE SE JUEGA Y SE JUNTAN TODOS LOS METODOS.
     * 
     * @param carton Carton ya generado en el propio main
     * @param bombo Bombo ya inicializado en el propio main
     * @param contador Contador que será usado para llevar la cuenta de los números extraídos del bombo
     * @param contadorAciertos Contador array unidimensional de tamaño 1 que será usado para llevar la cuenta de los números acertados
     * @param continuar Booleano que, si es true, se puede continuar jugando, si es false, se acaba el juego
     * @param registroNumsAciertos Array de los números acertados
     * @param registroNumerosBombo Array de los números extraídos del bombo
     */
    public static void jugar(String[][] carton, int[] bombo, int[] contador, int[] contadorAciertos, boolean continuar, int[] registroNumsAciertos, int[] registroNumerosBombo) {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        int maximoNumero = 90;
        boolean continuarBombo = true;
        boolean unaLinea = false;

        while (continuar && continuarBombo) {
            
            mostrarCarton(carton);
            // mostrarBombo(bombo);
            
            num = extraerNumero(bombo, contador, maximoNumero, continuarBombo); // Extraemos un numero del bombo, SI NO HAY NUMEROS EN EL BOMBO, SE ACABA EL JUEGO
            
            System.out.println("El numero extraido es: " + num);
            
            marcarCartonYAlmacenarEnciertos(carton, num, registroNumsAciertos, contadorAciertos); // Marcamos el numero en el carton
            
            
            //JORDI, AQUI TIENES LO DE SEGUIR, SI LO DESCOMENTAS YA FUNCIONA :)
            // SEGUIR BOMBO OPCION (S/N)

            /*System.out.println("Quieres sacar otro numero? (s/n)");
            char respuesta = sc.next().charAt(0);
            if (respuesta != 's') {
                continuarBombo = false;
            }*/

            // COMPROBAR LINEA
            if (utilitats.linea(carton)&& !unaLinea)  {
                System.out.println("LINEA!");
                mostrarCarton(carton);
                unaLinea = true;
            }

            if (utilitats.bingo(carton)) {
                System.out.println("BINGO!");
                mostrarCarton(carton);
                continuar = false;
                System.out.println("Quieres jugar otra partida? (s/n)");
                char otraPartida =  sc.next().charAt(0);
                if (otraPartida != 's') {
                    continuar = false;
                    
                } else {
                    reiniciarPartida(carton, bombo, contador, contadorAciertos);
                    continuar = true;
                }
            }
        }
        mostrarAciertos(registroNumsAciertos);
    }
    /**
     * Muestra los números acertados del bombo
     * @param registro Array de los números acertados
     */
    public static void mostrarAciertos(int[] registro) {

        System.out.println("NUMEROS ACERTADOS DEL BOMBO:");

        for (int i = 0; i < registro.length; i++) {
            System.out.printf("%3d",registro[i]);
        }
    }

// 2. JUEGO
    /**
     * Reinicia la partida, generando un nuevo cartón, inicializando el bombo y reiniciando el contador
     * @param carton Carton ya generado
     * @param bombo Bombo ya inicializado
     * @param contador Contador de los números extraídos del bombo
     * @param contadorAciertos
     */
    public static void reiniciarPartida(String[][] carton, int[] bombo, int[] contador, int[] contadorAciertos) {
        generarCarton(carton);
        inicializarBombo(bombo);
        contador[0] = 0;
        contadorAciertos[0] = 0;
    }

    /**
     * Comprueba si se tiene que continuar jugando mediante un booleano
     * @param carton Carton, ya generado y marcado
     * @return boolean, si es true, se puede continuar jugando, si es false, se acaba el juego
     */
    public static boolean continuarJuego(String[][] carton) {
        boolean continuar = false;
        int contador = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (carton[i][j].equals("X"))  {
                    contador++;
                }
            }
        }
        if (contador == 15) {
            continuar = true;
            return continuar;
            
        }
        if (contador < 15) {
            contador = 0;
        }

        return continuar;
    }

    /**
     * Genera un cartón de bingo
     * @param carton Cartón vacío (ARRAY DE STRING BIDIMENSIONAL)
     * @return El carton bidimensional ya generado (ARRAY DE STRINGS BIDIMENSIONAL)
     */
    public static String[][] generarCarton(String[][] carton) {
       
        int MAX = 90;
        int num = 0;
        int[] registre = new int[91];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                num = RandomNoRepetit(registre, MAX);
                
                carton[i][j] = String.valueOf((num));
            }
            
            int[] posicionesVacias = posicionesUnicas(9, 4); // Generar 4 posiciones únicas en una fila
            for (int posicion : posicionesVacias) {
                carton[i][posicion] = "@"; // Marcar las posiciones con "@"
            }
    }


        return carton;
    }

    

    // INICIALIZAR BOMBO
    /**
     * Inicializa el bombo con los números del 1 al 90
     * @param bombo Array unidimensional de 91 posiciones
     * @return Array de 91 posiciones, con numeros aleatorios no repetidos.
     */
    public static int[] inicializarBombo(int[] bombo) {
        int MAX = 90;
        int[] registre = new int[91];
        int opcion = 0;

        for (int i = 0; i < 90; i++) {
            int numero = RandomNoRepetit(registre, MAX);
            bombo[i] = numero;
        }

        return bombo;
    }
   
    /**
     * Extrae un número del bombo, si no quedan números, nos avisa y para el juego mediante el boleano continuarBombo, que lo pondrá en false.
     * @param bombo Bombo ya inicializado
     * @param contador Contador de los números extraídos, una vez llega a 90, nos avisa de que, por supuesto, ya no nos quedan mas numeros.
     * @param maximo Número máximo de números en el bombo
     * @param continuarBombo Booleano que, si se pone en false, para el juego ya que no quedan
     * @return El numero del bombo extraído de manera aleatoria
     */
    public static int extraerNumero(int[] bombo, int[] contador, int maximo, boolean continuarBombo) {
        if (contador[0] >= maximo) {
            System.out.println("No quedan mas numeros en el bombo");
            continuarBombo = false;
            return -1;
        }
        contador[0]++;
        return bombo[contador[0] ];
    
    }

    /**
     * Marca el cartón, cuando sale el numero deseado y almacena los números del bombo
     * @param carton Cartón del jugador
     * @param num Número extraído del bombo
     * @param registro Registro de los números extraídos del bombo que han sido enciertos
     * @param contador Contador de los números extraídos del bombo
     */
    public static void marcarCartonYAlmacenarEnciertos(String[][] carton, int num, int registro[], int[] contador) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (carton[i][j].equals(String.valueOf(num))) {
                    registro[contador[0]] = num;
                    contador[0]++;
                    carton[i][j] = "X"; // Marcamos con una X los numeros que coincidan
                    System.out.println("Este numero: " + num + " lo tenias en tu carton, te lo marco :)");
                    
                    
                }
            }
        }
    }

    /**
     * Comprueba si hay bingo en el cartón
     * @param carton
     * @return boolean, si es true hay bingo y si es false no lo hay
     */
    public static boolean bingo(String[][] carton) {
        boolean bingo = false;
        int contador = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (carton[i][j].equals("X") ) {
                    
                    contador++;
                }
            }
        }
        if (contador == 15) {
            bingo = true;
        }
        return bingo;

    }
    /**
     * Comprueba si hay una línea en el cartón
     * @param carton
     * @return boolean, si es true hay línea y si es false no la hay
     */
    public static boolean linea(String[][] carton) {
        int contador = 0;
        for (int i = 0; i < 3; i++) { // Recorre cada fila del cartón
            contador = 0;
            for (int j = 0; j < 9; j++) { // Recorre cada columna de la fila
                if (carton[i][j].equals("X")) { // Verifica si el número está marcado
                    contador++;
                }
            }
            if (contador == 5) { // Si hay 5 "X" en la fila, es una línea
                return true;
            }
        }
        return false;
    }

    //SOPORTE
    /**
     * Muestra el cartón ya generado
     * @param carton Cartón ya generado
     */
    public static void mostrarCarton(String[][] carton) {
        System.out.println("            ESTADO DEL CARTON:");
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 9; j++) {
                System.out.printf("%4s", carton[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        }
    /**
     * Muestra el bombo ya inicializado
     * @param bombo Bombo ya inicializado
     */
    public static void mostrarBombo(int[] bombo) {
        System.out.println("ESTADO DEL BOMBO: \n");
            int contador = 0;
            for (int i = 1; i <= 90; i++) {
                System.out.printf("%4d", bombo[i]);
                contador++;
                if (contador % 10 == 0) {
                    System.out.println();
                }
            }
        
        System.out.println();
    }

    /**
     * Obtener un número aleatorio entre un rango (min y max) 
     * Funciona de manera que, si le pasamos un minimo de 1 y un maximo de 90, nos devolverá un número aleatorio entre 1 y 90.
     * Podemos usarlo para generar números aleatorios en el bombo y en el cartón.
     * @param min Numero minimo
     * @param max Numero maximo
     * @return Numero aleatorio entre el rango
     */
    public static int obtenerAleatorio(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Genera un número aleatorio no repetido
     * @param Registre Array de 91 posiciones para registrar los números ya generados
     * @param MAX Número máximo, el cual se lo vamos a dar al metodo ObtenerAleatorio anteriormente generado
     * @return Nos devuelve un numero valido, que no se ha repetido, el cual se usará para generar el cartón y el bombo
     */
    public static int RandomNoRepetit(int[] Registre, int MAX) {
        int num;
        boolean valid = false;
        do {
        num = obtenerAleatorio(1, MAX);
        
        if (Registre[num] == 0) {
            Registre[num] = 1;
            valid = true;    
        }
    } while (!valid);
        
    return num;

    }
    /**
     * Genera posiciones únicas en un rango, lo usamos para generar huecos de manera aleatoria en el cartón.
     * @param rango Numero maximo de posiciones
     * @param cantidad Cantidad de posiciones que queremos generar
     * @return Array de posiciones únicas
     */
    public static int[] posicionesUnicas(int rango, int cantidad) {
        int[] posiciones = new int[cantidad];
        boolean[] ocupadas = new boolean[rango];
        int count = 0;
        
        while (count < cantidad) {
            int posicion = obtenerAleatorio(0, rango - 1);
            if (!ocupadas[posicion]) {
                ocupadas[posicion] = true;
                posiciones[count] = posicion;
                count++;
            }
        }
        return posiciones;
    }
    /** 
     * @param mensaje 
     * @return
     */
    public static int llegirInt(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextInt();
    }

    //BANNER !!!

    /**
     * Funcion para mostrar Banner
     */
    
    public static void mostrarBannerBingo() {
        // ANSI color codes
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
    
        System.out.println(cyan + ">>============================<<");
        System.out.println("|| " + red + "____ ___ _   _  ____  ___  " + cyan + "||");
        System.out.println("||" + green + "| __ )_ _| \\ | |/ ___|/ _ \\ " + cyan + "||");
        System.out.println("||" + yellow + "|  _ \\| ||  \\| | |  _| | | " + cyan + " ||");
        System.out.println("||" + blue + "| |_) | || |\\  | |_| | |_| " + cyan + " ||");
        System.out.println("||" + purple + "|____/___|_| \\_|\\____|\\___/ " + cyan + "||");
        System.out.println(">>============================<<" + reset);
    
        System.out.println();
        System.out.println(green + "🎉 PREPARATE PARA JUGAR LA PARTIDA MAS DIVERTIDA DE TU VIDA!!! 🎉" + reset);
        System.out.println();
    }
    
        // DESPEDIDA

    /**
     * Funcion para mensaje de despedida.
     */
        public static void despedida() {
            System.out.println("GRACIAS POR JUGAR AL BINGO");
        }
    

    

    
}
