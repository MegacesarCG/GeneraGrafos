package Grafos;

import Dibujo.*;
import obtencionDatos.*;
import matrices.*;
import validadores.*;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 *
 * @author heisei
 */
public class Grafos {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Sistema de creación y graficación de grafos.\nAutores: César Zabala, Jesús González, Ulises y Daniela");
        int numeroNodos = PedidorDatos.pedirNodos();
        Adyacencia matriz = new Adyacencia(numeroNodos); //Matriz de adyacencia
        JOptionPane.showMessageDialog(null, "Matriz de adyacencia creada satisfactoriamente.");
        
        boolean graficar = true;
        char respuestas;
        
        // Se pregunta si desea añadir aristas
        respuestas = JOptionPane.showInputDialog("¿Desea agregar alguna arista? (s/n)").charAt(0);
        if (!(Character.toString(respuestas).equalsIgnoreCase("s") || Character.toString(respuestas).equalsIgnoreCase("n"))) {
            boolean salir = false;
            while (!salir) {
                respuestas = JOptionPane.showInputDialog("Entrada invalida. ¿Desea agregar alguna arista? (s/n)").charAt(0);
                salir = ((Character.toString(respuestas).equalsIgnoreCase("s") || Character.toString(respuestas).equalsIgnoreCase("n")));
            }
        }
        if (respuestas == 's') {
            graficar = false;
        }
        
        //Proceso en donde se conectan los vertices con las aristas
        while (!graficar) {
            int origen;
            int destino;
            char resp;
            origen = PedidorDatos.pedirOrigen(matriz.getMatriz());
            destino = PedidorDatos.pedirDestino(matriz.getMatriz());
            do {
                resp = JOptionPane.showInputDialog("¿Que tipo de arista desea añadir entre los nodos?\n1.- Dirigida\n2.- No dirigida").charAt(0);
            } while (!(resp == '1' || resp == '2'));
            //Si el tipo de arista es dirijida
            if (resp == '1') { //dirigida
                int numeroAristas = PedidorDatos.pedirNumeroAristas();
                if (!PedidorDatos.sePuedenAgregarMasAristas(matriz.getMatriz(), (origen - 1), (destino - 1), numeroAristas)) {
                    JOptionPane.showMessageDialog(null, "No pueden agregarse esta cantidad de aristas entre estos dos nodos de esta manera.");
                } else {
                    for (int i = 0; i < numeroAristas; i++) {
                        matriz.añadirAristaDirigida((origen - 1), (destino - 1));
                    }
                    JOptionPane.showMessageDialog(null, "Aristas dirigidas añadidas satisfactoriamente.");
                }         
            } //Si el tipo de arista no es dirijida
            else if (resp == '2') { //No dirigida
                int numeroAristas = PedidorDatos.pedirNumeroAristas();
                if (!(PedidorDatos.sePuedenAgregarMasAristas(matriz.getMatriz(), (origen - 1), (destino - 1), numeroAristas) && PedidorDatos.sePuedenAgregarMasAristas(matriz.getMatriz(), (destino-1), (origen-1), numeroAristas))) {
                    JOptionPane.showMessageDialog(null, "No pueden agregarse esta cantidad de aristas entre estos dos nodos de esta manera.");
                } else {
                    for (int i = 0; i < numeroAristas; i++) {
                        matriz.añadirAristaNoDirigida((origen - 1), (destino - 1));
                    }
                    JOptionPane.showMessageDialog(null, "Aristas no dirigidas añadidas satisfactoriamente.");
                }
            }
            char respuest;
            
            //Se repite el bucle hasta que escriba n
            do {
                respuest = JOptionPane.showInputDialog("¿Desea agregar otro vertice entre nodos? (s/n)").charAt(0);
            } while (!(respuest == 's' || respuest == 'n'));
            if (respuest == 'n') {
                graficar = true;
            }
        }
        
        //Determina el tipo base del grafo
        matriz.determinarTipoBase();
        //Determina el subtipo del grafo
        switch (matriz.getTipoBase()) {
            case "Dirigido":
                matriz.determinarTipoSecundarioDirigido();
                matriz.determinarExistenciaCaminoEulerDirigido();
                break;
            case "No dirigido":
                matriz.determinarTipoSecundarioDirigido();
                matriz.determinarExistenciaCaminoEulerNoDirigido();
                break;
        }

        //Parte grafica del proyecto (Por Cesar Zabala)
        //Creacion de clases
        JFrame f = new JFrame("Representacion de Grafo");
        PanelDibujo panel = new PanelDibujo();

        //Asigna los valores necesarios de la matriz creada
        panel.setAdyacencia(matriz);

        //Visualizacion de la ventana
        f.add(panel);
        f.setBounds(200, 150, 640, 480);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Una vez cerrado se culmina el programa
        f.setVisible(true);

    }
}
