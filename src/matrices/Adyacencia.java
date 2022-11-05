package matrices;

public class Adyacencia {

    private int nodos;
    private int matriz[][];
    private final String[] opcionesTipoBase = {"No dirigido", "Dirigido"};
    private String tipoBase;
    private final String[] opcionesTipoSecundarioNoDirigido = {"Simple", "Multigrafo", "Pseudografo"};
    private final String[] opcionesTipoSecundarioDirigido = {"Simple", "Multigrafo"};
    private String tipoSecundario;
    private boolean caminoEuler;

    public Adyacencia(int cantidadNodos) {
        this.nodos = cantidadNodos;
        matriz = new int[this.nodos][this.nodos];
        this.tipoBase = "Nulo";
        this.caminoEuler = false;
    }

    public void setTipo(int i) {
        if (i == -1) {
            this.tipoBase = "Nulo";
        } else {
            this.tipoBase = this.opcionesTipoBase[i];
        }
    }

    public String getTipo() {
        return this.tipoBase;
    }

    public void setTipoSecundarioNoDirigido(int opcion) {
        this.tipoSecundario = this.opcionesTipoSecundarioNoDirigido[opcion];
    }

    public void setTipoSecundarioDirigido(int opcion) {
        this.tipoSecundario = this.opcionesTipoSecundarioDirigido[opcion];
    }

    public boolean estaVacia() {
        for (int i = 0; i < this.nodos; i++) {
            for (int j = 0; j < this.nodos; j++) {
                if (!(this.matriz[i][j] == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int cantidadTotalBucles() {
        int contador = 0;
        for (int i = 0; i < this.nodos; i++) {
            for (int j = 0; j < this.nodos; j++) {
                if (i == j) {
                    if (!(this.matriz[i][j] == 0)) {
                        contador += this.matriz[i][j];
                    }
                }
            }
        }
        return contador;
    }

    public void añadirAristaNoDirigida(int origen, int destino) {
        matriz[origen][destino] += 1;
        matriz[destino][origen] += 1;
    }

    public void añadirAristaDirigida(int origen, int destino) {
        matriz[origen][destino] += 1;
    }

    public void imprimirMatriz() {
        for (int i = 0; i < this.nodos; i++) {
            for (int j = 0; j < this.nodos; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void determinarTipoBase() {
        int A = 0;
        int B = 0;
        
        int i = 0;
        while (i < this.nodos){
            for (int j = i; j < this.nodos; j++) {
                if(i != j){
                   A += matriz[i][j];
                   B += matriz[j][i];
                }
            }
            i++;
        }
        
        //La sumatoria es igual
        if (A == B) {
            int Total = A+B;
            if (Total == 0) {
                setTipo(-1); //Nulo
            } else {
                setTipo(0); // No Dirijido
            }
        } else {
            setTipo(1); //Dirijido
        }
    }

    public void determinarTipoNoDirigido() {
        int seleccion = -1;
        boolean pseudografo = false;
        boolean multigrafo = false;
        for (int i = 0; i < nodos; i++) { //Determinar si es un pseudografo
            for (int j = 0; j < nodos; j++) {
                if (i == j) {
                    if (!(matriz[i][j] == 0)) {
                        seleccion = 2;
                        pseudografo = true;
                    }
                }
            }
        }

        if (!pseudografo) {
            for (int i = 0; i < nodos; i++) { //Determinar si es un multigrafo
                for (int j = 0; j < nodos; j++) {
                    if (!(i == j)) {
                        if (matriz[i][j] > 1) {
                            seleccion = 1;
                            multigrafo = true;
                        }
                    }
                }
            }
            
            if (!multigrafo) {
                if (seleccion == -1) { //Si no es pseudografo ni es multigrafo, debe ser un grafo simple.
                    seleccion = 0;
                }
            }
        }
        setTipoSecundarioNoDirigido(seleccion);
    }

    public void determinarTipoSecundarioDirigido() {
        int seleccion = -1;
        for (int i = 0; i < this.nodos; i++) {  //Determinar si es multigrafo
            for (int j = 0; j < this.nodos; j++) {
                if ((matriz[i][j]) > 1) {
                    seleccion = 1;
                }
            }
        }
        if (seleccion == -1) { //Si no es multigrafo, es simple
            seleccion = 0;
        }
        setTipoSecundarioDirigido(seleccion);
    }

    public void determinarExistenciaCaminoEulerNoDirigido() {
        int grado;
        int nodosConGradoImpar = 0;
        boolean salidaTemprana = false;
//        for (int i = 1; i < this.nodos; i++) { //Determinar si el grafo es conexo
//            for (int j = 1; j < this.nodos; j++) {
//                if (!(i == j)) { //Evitar el evaluar la diagonal principal
//                    if (matriz[i][j] == 0) { //Como la matriz de adyacencia de un grafo no dirigido es simetrica, si hay 0 caminos de una arista a otra, significa que no hay un camino.
//                        this.caminoEuler = false;
//                        salidaTemprana = true;
//                        break;
//                    }
//                }
//                if (salidaTemprana = true) {
//                    break;
//                }
//            }
        if (!(Operaciones.esConexo(matriz))) { //Determinar si el grafo es conexo con matriz de caminos.
            salidaTemprana = true;
        }

        if (!(salidaTemprana)) {
            for (int i = 1; i < this.nodos; i++) { //Determinar el numero de aristas impares
                grado = 0;
                for (int j = 1; j < this.nodos; j++) {
                    if (i == j) {
                        grado = grado + (matriz[i][j] * 2);
                    } else {
                        grado = grado + matriz[i][j];
                    }
                }
                if (!(grado % 2 == 0)) {
                    nodosConGradoImpar = nodosConGradoImpar + 1;
                }
            }
            this.caminoEuler = ((nodosConGradoImpar == 0) || (nodosConGradoImpar == 2));
        }
    }

    public void determinarExistenciaCaminoEulerDirigido() {
        int[] gradosSalida = new int[this.matriz.length];
        int[] gradosEntrada = new int[this.matriz.length];
        boolean[] igualdadDeGrados = new boolean[gradosEntrada.length];
        int filas = this.matriz.length;
        int columnas = this.matriz.length;
        int acumuladorSalida;
        int acumuladorEntrada;
        boolean salidaTemprana = false;
        this.caminoEuler = false;

        if (!(Operaciones.esConexo(this.matriz))) { //Revisar si grafo es conexo.
            salidaTemprana = true;
        }

        if (!(salidaTemprana)) { //Si el grafo es conexo evaluar el ciclo primero
            for (int i = 0; i < filas; i++) { //Obtener el grado de entrada y salida de cada nodo.
                acumuladorSalida = 0;
                acumuladorEntrada = 0;
                for (int j = 0; j < columnas; j++) {
                    acumuladorSalida = acumuladorSalida + this.matriz[i][j];
                    acumuladorEntrada = acumuladorEntrada + this.matriz[j][i];
                }
                gradosSalida[i] = acumuladorSalida;
                gradosEntrada[i] = acumuladorEntrada;
            }

            //Revisar si hay un circuito primero...
            for (int i = 1; i < gradosSalida.length; i++) {
                if (gradosSalida[i] == gradosEntrada[i]) {
                    igualdadDeGrados[i] = true;
                }
            }
            int contador = 0;
            for (int i = 1; i < igualdadDeGrados.length; i++) {
                if (igualdadDeGrados[i] == true) {
                    contador += 1;
                }
            }
            
            this.caminoEuler = (contador == igualdadDeGrados.length);

            if (!(contador == igualdadDeGrados.length)) { //Si no hay ciclo, revisar camino.
                boolean condicion1, condicion2, condicion3;

                //Evaluar cantidad de nodos que cumplen con la condicion 1: gradosalida - gradoentrada = 1
                int contadorCondicion1 = 0;
                for (int i = 1; i < gradosSalida.length; i++) {
                    if ((gradosSalida[i] - gradosEntrada[i]) == 1) {
                        contadorCondicion1 += 1;
                    }
                }
                //Evaluar cantidad de nodos que cumplen con la condicion 2: gradosentrada - gradosalida = 1;
                int contadorCondicion2 = 0;
                for (int i = 0; i < gradosEntrada.length; i++) {
                    if ((gradosEntrada[i] - gradosSalida[i]) == 1) {
                        contadorCondicion2 += 1;
                    }
                }
                //Si hay exactamente un nodo que cumple la condicion 1, y exactamente 1 nodo que cumple la condicion 2...
                if ((contadorCondicion1 == 1) && (contadorCondicion2 == 1)) {
                    //Evaluar la tercera condicion; que el resto de los nodos sean iguales.
                    int contadorReverso = igualdadDeGrados.length;
                    for (int i = 1; i < gradosSalida.length; i++) {
                        if (gradosSalida[i] == gradosEntrada[i]) {
                            igualdadDeGrados[i] = true;
                        }
                    }
                    for (boolean e: igualdadDeGrados) {
                        if (e==false){
                            contadorReverso = contadorReverso - 1;
                        }
                    }
                    this.caminoEuler = (contadorReverso == igualdadDeGrados.length-2); //Si hay solo 2 nodos que no son iguales, lo que significa que el resto lo son...
                    //Hay un camino euleriano.
                } else {
                    this.caminoEuler = false;
                }
            }
        }
    }
    
    public boolean getEuler(){
        return this.caminoEuler;
    }
    
    public int[][] getMatriz(){
        return this.matriz;
    }
    
    public int getNodos(){
        return this.nodos;
    }
    
    public String getTipoBase(){
        return this.tipoBase;
    }
    
    public String getTipoSecundario(){
        return this.tipoSecundario;
    }
}
