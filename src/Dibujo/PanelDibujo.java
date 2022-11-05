/*
 * Cesar Zabala
 * Dibujador de grafos
 * Entran Matrices de Adyacencia, salen grafos XD
 */
package Dibujo;

import java.awt.*;
import java.awt.geom.*;
import static java.lang.Math.*;
import javax.swing.*;
import matrices.Adyacencia;

/**
 *
 * @author Cesar Zabala
 */
public class PanelDibujo extends JPanel {

    private int Matriz[][];
    private int lados;
    private boolean Es_Dirijido;
    private String Tipo, Subtipo;
    private String euler;

    public void setAdyacencia(Adyacencia Adya) {
        //Asigna los valores del arreglo
        Matriz = Adya.getMatriz();
        //Asigna la cantidad de lados
        lados = Adya.getNodos();

        //Asigna si es dirijido o no.
        Es_Dirijido = Adya.getTipoBase().equals("Dirigido");
        Tipo = Adya.getTipoBase();
        
        //Asigna el tipo secundario, si es valido
        if (Adya.getTipoSecundario() != null) {
            Subtipo = Adya.getTipoSecundario();
        } else {
            Subtipo = "---";
        }
        
        //Asigna el fragmento de texto si existen caminos eulerianos
        if (Adya.getEuler() == true) {
            euler = "Existen";
        } else {
            euler = "No existen";
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        //Nuestras "brochas"
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Definicion de variables-constantes
        int k = 10; //Tamano de las flechas (pixeles)
        int pro = 40; //Pronunciacion de las curvas
        int size = this.getWidth() / 6; //Distancia de separacion de los puntos con respecto al eje (0,0)
        double angle = 2 * Math.PI / lados; //Seno y coseno trabajan con radianes  
        int nodoPos[][] = new int[lados][2]; //nodoPos almacenara los valores X y Y de cada vertice 

        //Cambia el punto de origen (0,0) al centro de la pantalla
        g.translate((this.getWidth() / 2), (this.getHeight() / 2));
        g.setColor(Color.black);

        int i = 0;
        //Bucle para dibujar la figura
        if (lados == 1) {
            //Dibuja un punto en el centro
            g.fillOval(-5, -5, 10, 10); //(Pos X, Pos Y, size X, size Y)
            nodoPos[i][0] = 0;
            nodoPos[i][1] = 0;
        } else {
            while (i < lados) {
                int x = (int) Math.round(size * cos(i * angle)); //Calcula y redondea "X"
                int y = (int) Math.round(size * sin(i * angle)); //Calcula y redondea "Y"
                //Almacena las posiciones en una matriz para dibujar las conexiones mas adelante
                nodoPos[i][0] = x;
                nodoPos[i][1] = y;
                //Dibuja los puntos
                g.fillOval(x - 5, y - 5, 10, 10); //(Pos X, Pos Y, size X, size Y)
                String No = String.valueOf(i + 1);
                g.setColor(Color.red);
                g.drawString(No, x - 15, y + 15);
                g.setColor(Color.black);
                i++;
            }
        }
        //Dibuja bucles
        for (i = 0; i < lados; i++) { //Recorre la diagonal principal
            if (Matriz[i][i] > 0) { //Detecta si hay bucles
                int bucle = Matriz[i][i];

                while (bucle > 0) {
                    int x1;
                    int y1;
                    int x2;
                    int y2;
                    int x3;
                    int y3;

                    //Si el grafo posee mas de un lado
                    if (lados > 1) {
                        //1er punto
                        x1 = (int) Math.round((size + pro * bucle) * cos(i * angle)); //Calcula y redondea "X"
                        y1 = (int) Math.round((size + pro * bucle) * sin(i * angle)); //Calcula y redondea "Y"
                        //2do punto
                        x2 = (int) Math.round((size + (pro / 2) * bucle) * cos(i * angle - (0.1 * bucle))); //Calcula y redondea "X"
                        y2 = (int) Math.round((size + (pro / 2) * bucle) * sin(i * angle - (0.1 * bucle))); //Calcula y redondea "Y"
                        //3er punto
                        x3 = (int) Math.round((size + (pro / 2) * bucle) * cos(i * angle + (0.1 * bucle))); //Calcula y redondea "X"
                        y3 = (int) Math.round((size + (pro / 2) * bucle) * sin(i * angle + (0.1 * bucle))); //Calcula y redondea "Y"
                    } //Si solo posee uno
                    else {
                        //1er punto
                        x1 = 0;
                        y1 = -40 * bucle;
                        //2do punto
                        x2 = -20 * bucle;
                        y2 = -20 * bucle;
                        //3er punto
                        x3 = 20 * bucle;
                        y3 = -20 * bucle;
                    }
                    //Dibujando bucle
                    g.drawLine(nodoPos[i][0], nodoPos[i][1], x2, y2);
                    g.drawLine(nodoPos[i][0], nodoPos[i][1], x3, y3);
                    g.drawLine(x2, y2, x1, y1);
                    g.drawLine(x3, y3, x1, y1);
                    bucle--;
                    //Dibujara una flecha solo si es dirijido
                    if (Es_Dirijido == true) {
                        double alfa = Math.atan2(nodoPos[i][1] - y3, nodoPos[i][0] - x3);

                        //Coloca una flecha
                        int xa = (int) (nodoPos[i][0] - k * Math.cos(alfa + 1));
                        int ya = (int) (nodoPos[i][1] - k * Math.sin(alfa + 1));
                        g.drawLine(xa, ya, nodoPos[i][0], nodoPos[i][1]);
                        xa = (int) (nodoPos[i][0] - k * Math.cos(alfa - 1));
                        ya = (int) (nodoPos[i][1] - k * Math.sin(alfa - 1));
                        g.drawLine(xa, ya, nodoPos[i][0], nodoPos[i][1]);
                    }
                }
            }

        }
        //Dibuja aristas entre cada lado
        for (i = 0; i < lados; i++) {
            //En caso de que el grafo sea dirijido
            if (Es_Dirijido == true) {
                for (i = 0; i < lados; i++) { //Recorre filas
                    for (int j = 0; j < lados; j++) { //Recorre Columnas
                        if (i != j) { //No se valen diagonales principales
                            if (Matriz[i][j] > 0) { //Hay un camino entre i y j
                                g.drawLine(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0], nodoPos[j][1]);
                                double alfa = Math.atan2(nodoPos[j][1] - nodoPos[i][1], nodoPos[j][0] - nodoPos[i][0]);

                                //Coloca una flecha
                                int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                            }

                            //Hay dos caminos entre i y j
                            if (Matriz[i][j] > 1) {

                                //Si la posicion x de i y j son iguales. (i = -j)
                                if (nodoPos[i][0] == -nodoPos[j][0]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion x entre i y j
                                    int medio = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], medio, nodoPos[j][1] + pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - (nodoPos[j][1] + pro), nodoPos[j][0] - medio);

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);

                                } //Si la posicion y de i y j son iguales (i = -j)
                                else if (nodoPos[i][1] == -nodoPos[j][1]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0] + pro, medio, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - medio, nodoPos[j][0] - (nodoPos[j][0] + pro));

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                } //Si el valor absoluto de la posicion (x,y) de "i" coincide con al menos con el valor absoluto
                                //de la posicion (x,y) de "j"
                                else if (abs(nodoPos[i][0]) == abs(nodoPos[j][1]) || abs(nodoPos[i][1]) == abs(nodoPos[j][0])) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0], nodoPos[i][1], nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                }//Si ninguna posicion coincide
                                else {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Guarda la posicion (x,y) entre i y j
                                    int x = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    int y = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], x + pro, y + pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - (y + pro), nodoPos[j][0] - (x + pro));

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                }
                            }

                            //Hay tres caminos entre i y j
                            if (Matriz[i][j] > 2) {

                                //Si la posicion x de i y j son iguales. (i = -j)
                                if (nodoPos[i][0] == -nodoPos[j][0]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion x entre i y j
                                    int medio = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], medio, nodoPos[j][1] - pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - (nodoPos[j][1] - pro), nodoPos[j][0] - medio);

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                } //Si la posicion y de i y j son iguales (i = -j)
                                else if (nodoPos[i][1] == -nodoPos[j][1]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0] - pro, medio, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - medio, nodoPos[j][0] - (nodoPos[j][0] - pro));

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                } //Si el valor absoluto de la posicion (x,y) de "i" coincide con al menos con el valor absoluto
                                //de la posicion (x,y) de "j"
                                else if (abs(nodoPos[i][0]) == abs(nodoPos[j][1]) || abs(nodoPos[i][1]) == abs(nodoPos[j][0])) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[i][0], nodoPos[j][1], nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                }//Si ninguna posicion coincide
                                else {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    int x = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    int y = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], x - pro, y - pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                    double alfa = Math.atan2(nodoPos[j][1] - (y + pro), nodoPos[j][0] - (x + pro));

                                    //Coloca una flecha
                                    int xa = (int) (nodoPos[j][0] - k * Math.cos(alfa + 1));
                                    int ya = (int) (nodoPos[j][1] - k * Math.sin(alfa + 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                    xa = (int) (nodoPos[j][0] - k * Math.cos(alfa - 1));
                                    ya = (int) (nodoPos[j][1] - k * Math.sin(alfa - 1));
                                    g.drawLine(xa, ya, nodoPos[j][0], nodoPos[j][1]);
                                }
                            }
                        }
                    }
                }
                //En caso de grafos no dirijidos
            } else {
                for (i = 0; i < lados; i++) { //Recorre filas
                    for (int j = 0; j < lados; j++) { //Recorre Columnas
                        if (i != j) { //No se valen diagonales principales
                            if (Matriz[i][j] > 0) { //Hay un camino entre i y j
                                g.drawLine(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0], nodoPos[j][1]);
                            }

                            //Hay dos caminos entre i y j
                            if (Matriz[i][j] > 1) {

                                //Si la posicion x de i y j son iguales. (i = -j)
                                if (nodoPos[i][0] == -nodoPos[j][0]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion x entre i y j
                                    int medio = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], medio, nodoPos[j][1] + pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                } //Si la posicion y de i y j son iguales (i = -j)
                                else if (nodoPos[i][1] == -nodoPos[j][1]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0] + pro, medio, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                }//Si el valor absoluto de la posicion (x,y) de "i" coincide con al menos con el valor absoluto
                                //de la posicion (x,y) de "j"
                                else if (abs(nodoPos[i][0]) == abs(nodoPos[j][1]) || abs(nodoPos[i][1]) == abs(nodoPos[j][0])) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0], nodoPos[i][1], nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                } //Si ninguna posicion coincide
                                else {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Guarda la posicion (x,y) entre i y j
                                    int x = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    int y = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], x + pro, y + pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                }
                            }

                            //Hay tres caminos entre i y j
                            if (Matriz[i][j] > 2) {

                                //Si la posicion x de i y j son iguales. (i = -j)
                                if (nodoPos[i][0] == -nodoPos[j][0]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion x entre i y j
                                    int medio = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], medio, nodoPos[j][1] - pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                } //Si la posicion y de i y j son iguales (i = -j)
                                else if (nodoPos[i][1] == -nodoPos[j][1]) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[j][0] - pro, medio, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);

                                } //Si el valor absoluto de la posicion (x,y) de "i" coincide con al menos con el valor absoluto
                                //de la posicion (x,y) de "j"
                                else if (abs(nodoPos[i][0]) == abs(nodoPos[j][1]) || abs(nodoPos[i][1]) == abs(nodoPos[j][0])) {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    //Medio es la posicion y entre i y j
                                    int medio = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], nodoPos[i][0], nodoPos[j][1], nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                } //Si ninguna posicion coincide
                                else {
                                    QuadCurve2D q = new QuadCurve2D.Float();
                                    int x = (nodoPos[i][0] + nodoPos[j][0]) / 2;
                                    int y = (nodoPos[i][1] + nodoPos[j][1]) / 2;
                                    q.setCurve(nodoPos[i][0], nodoPos[i][1], x - pro, y - pro, nodoPos[j][0], nodoPos[j][1]);
                                    g2.draw(q);
                                }
                            }
                        }
                    }
                }
            }
        }

        //Escribe los tipos de grafos
        int line = this.getHeight() / 3 + 30;
        int start = -(this.getWidth() / 2) + 10;
        g.setColor(Color.cyan);
        g.fillRoundRect(start - 5, line - 30, 200, 70, 7, 7);
        g.setColor(Color.black);
        g.drawRoundRect(start - 5, line - 30, 200, 70, 7, 7);
        //Dibuja Tipo
        String L1 = "Tipo de Grafo: " + Tipo;
        g.drawString(L1, start, line);
        L1 = "Subtipo: " + Subtipo;
        g.drawString(L1, start, line + 15);
        L1 = euler + " caminos eulerianos";
        g.drawString(L1, start, line + 30);
    }
}
