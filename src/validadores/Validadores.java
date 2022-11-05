package validadores;

import javax.swing.JOptionPane;

public class Validadores {

    public static boolean validarInt(String s) { //Valida un entero
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String validarCadena(String mensaje) { //Valida que la cadena no esté vacía
        String entrada = JOptionPane.showInputDialog(mensaje);
        while (entrada.equals("")) {
            JOptionPane.showMessageDialog(null, "No se pueden introducir elementos vacios.");
            entrada = JOptionPane.showInputDialog(mensaje);
        }
        return entrada;
    }

    public static boolean validarNegativoCadena(String mensaje) {
        try {
            int numero = Integer.parseInt(mensaje);
            return (numero > 0);    
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean validarNegativo(int numero) { //Valida que el entero no sea negativo o cero
        boolean condicion;
        condicion = numero >= 0;
        return condicion;
    }
}
