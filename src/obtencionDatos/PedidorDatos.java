package obtencionDatos;

import javax.swing.JOptionPane;
import validadores.Validadores;

/**
 *
 * @author heisei
 */
public class PedidorDatos {

    public static int pedirNodos() {
        boolean salir;
        String validadorCadena;
        int validadorNegativo;
        validadorCadena = JOptionPane.showInputDialog("¿Cuantos nodos tiene el grafo? (1-10)");
        if (!Validadores.validarInt(validadorCadena)) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero invalido. ¿Cuantos nodos tiene el grafo? (1-10)");
                salir = Validadores.validarInt(validadorCadena);
            }
        }
        validadorNegativo = Integer.parseInt(validadorCadena);
        while (!Validadores.validarNegativo(validadorNegativo) || validadorNegativo <= 0 || validadorNegativo >= 11) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero de nodos no puede ser menor a 1 ni ser invalido.\n Ingrese un numero valido. (1-10)");
                salir = Validadores.validarNegativoCadena(validadorCadena);
            }
            validadorNegativo = Integer.parseInt(validadorCadena);
        }
        int nNodos = validadorNegativo;
        return nNodos;
    }

    public static int pedirOrigen(int[][] matriz) {
        int origen;
        boolean salir;
        String validadorCadena;
        int validadorNegativo;

        validadorCadena = JOptionPane.showInputDialog("¿Cual es el nodo de origen? (1 - " + matriz.length + ")");
        if (!Validadores.validarInt(validadorCadena)) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero invalido.\n ¿Cual es el nodo de origen? (1 - " + matriz.length + ")");
                salir = Validadores.validarInt(validadorCadena);
            }
        }
        validadorNegativo = Integer.parseInt(validadorCadena);
        if (!Validadores.validarNegativo(validadorNegativo)) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero no puede ser menor a 1 ni ser invalido.\n Ingrese un numero valido. (1 - " + matriz.length + ")");
                salir = Validadores.validarNegativoCadena(validadorCadena);
            }
            validadorNegativo = Integer.parseInt(validadorCadena);
        }
        if (validadorNegativo > matriz.length || validadorNegativo <= 0) {
            boolean salir2 = false;
            while (!salir2) {
                validadorCadena = JOptionPane.showInputDialog("El nodo de origen elegido no se encuentra en el grafo.\n Introduzca un nodo valido (1 - " + matriz.length + ")");
                if (!Validadores.validarInt(validadorCadena)) {
                    salir = false;
                    while (!salir) {
                        validadorCadena = JOptionPane.showInputDialog("Numero invalido.\n ¿Cual es el nodo de origen? (1 - " + matriz.length + ")");
                        salir = Validadores.validarInt(validadorCadena);
                    }
                }
                validadorNegativo = Integer.parseInt(validadorCadena);
                if (!Validadores.validarNegativo(validadorNegativo)) {
                    salir = false;
                    while (!salir) {
                        validadorCadena = JOptionPane.showInputDialog("Numero no puede ser menor a 1 ni ser invalido.\n Ingrese un numero valido. (1 - " + matriz.length + ")");
                        salir = Validadores.validarNegativoCadena(validadorCadena);
                    }
                    validadorNegativo = Integer.parseInt(validadorCadena);
                }
                salir2 = validadorNegativo <= matriz.length;
            }
        }
        origen = validadorNegativo;
        return origen;
    }

    public static int pedirDestino(int[][] matriz) {
        int destino;
        boolean salir;
        String validadorCadena;
        int validadorNegativo;
        validadorCadena = JOptionPane.showInputDialog("¿Cual es el nodo destino? (1 - " + matriz.length + ")");
        if (!Validadores.validarInt(validadorCadena)) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero invalido.\n ¿Cual es el nodo destino? (1 - " + matriz.length + ")");
                salir = Validadores.validarInt(validadorCadena);
            }
        }
        validadorNegativo = Integer.parseInt(validadorCadena);
        if (!Validadores.validarNegativo(validadorNegativo)) {
            salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Numero no puede ser menor a 1 ni ser invalido.\n Ingrese un numero valido. (1 - " + matriz.length + ")");
                salir = Validadores.validarNegativoCadena(validadorCadena);
            }
            validadorNegativo = Integer.parseInt(validadorCadena);
        }
        if (validadorNegativo > matriz.length || validadorNegativo <= 0) {
            boolean salir2 = false;
            while (!salir2) {
                validadorCadena = JOptionPane.showInputDialog("El nodo destino elegido no se encuentra en el grafo.\n Introduzca un nodo valido. (1 - " + matriz.length + ")");
                if (!Validadores.validarInt(validadorCadena)) {
                    salir = false;
                    while (!salir) {
                        validadorCadena = JOptionPane.showInputDialog("Numero invalido. ¿Cual es el nodo de origen? (1 - " + matriz.length + ")");
                        salir = Validadores.validarInt(validadorCadena);
                    }
                }
                validadorNegativo = Integer.parseInt(validadorCadena);
                if (!Validadores.validarNegativo(validadorNegativo)) {
                    salir = false;
                    while (!salir) {
                        validadorCadena = JOptionPane.showInputDialog("Numero no puede ser menor a 1 ni ser invalido.\n Ingrese un numero valido. (1 - " + matriz.length + ")");
                        salir = Validadores.validarNegativoCadena(validadorCadena);
                    }
                    validadorNegativo = Integer.parseInt(validadorCadena);
                }
                salir2 = validadorNegativo <= matriz.length;
            }
        }
        destino = validadorNegativo;
        return destino;
    }
    
    public static boolean sePuedenAgregarMasAristas(int[][] matriz, int posicion1, int posicion2, int numeroAristas){
        return matriz[posicion1][posicion2] + numeroAristas <= 3;
    }
    
    public static int pedirNumeroAristas(){
        int numeroAristas;
        String validadorCadena = JOptionPane.showInputDialog("Seleccione la cantidad de aristas que desea agregar (1-3).");
        if (!Validadores.validarNegativoCadena(validadorCadena)) {
            boolean salir = false;
            while (!salir) {
                validadorCadena = JOptionPane.showInputDialog("Selección invalida. Seleccione la cantidad de aristas que desea agregar (1-3).");
                salir = (Validadores.validarNegativoCadena(validadorCadena));
            }
        }
        numeroAristas = Integer.parseInt(validadorCadena);
        if (numeroAristas != 1 && numeroAristas != 2 && numeroAristas != 3) {
            boolean salir2 = false;
            while (!salir2) {
                validadorCadena = JOptionPane.showInputDialog("Numero fuera del limite. Seleccione la cantidad de aristas que desea agregar (1-3).");
                if (!Validadores.validarNegativoCadena(validadorCadena)) {
                    boolean salir = false;
                    while (!salir) {
                        validadorCadena = JOptionPane.showInputDialog("Selección invalida. Seleccione la cantidad de aristas que desea agregar (1-3).");
                        salir = (Validadores.validarNegativoCadena(validadorCadena));
                    }
                }
                salir2 = (Integer.parseInt(validadorCadena) > 0 || Integer.parseInt(validadorCadena) < 4);
            }
            numeroAristas = Integer.parseInt(validadorCadena);
        }
        return numeroAristas;
    }
    
}
