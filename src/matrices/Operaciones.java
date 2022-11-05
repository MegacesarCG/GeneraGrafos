package matrices;

public class Operaciones {

    public static int[][] sumadeMatrices(int[][] M1, int[][] M2, int m, int n) {
        int[][] MR = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                MR[i][j] = M1[i][j] + M2[i][j];
            }
        }
        return MR;
    }

    public static int[][] productoDeMatrices(int[][] M1, int[][] M2, int ma, int na, int mb, int nb) {
        int[][] MR = new int[ma][nb];
        for (int i = 0; i < ma; i++) {
            for (int j = 0; j < nb; j++) {
                MR[i][j] = 0;
                for (int k = 0; k < na; k++) {
                    MR[i][j] = MR[i][j] + (M1[i][k] * M2[k][j]);
                }
            }

        }
        return MR;
    }

    public static int[][] matrizDeCaminos(int[][] matrizOriginal) {
        int nodos = matrizOriginal.length;
        int nodos2 = matrizOriginal[0].length;

        int[][] matrizEstrella = new int[nodos][nodos2];
        matrizEstrella = sumadeMatrices(matrizEstrella, matrizOriginal, nodos, nodos2);
        int[][] matrizAcumuladora = productoDeMatrices(matrizOriginal, matrizOriginal, nodos, nodos2, nodos, nodos2);
        matrizEstrella = sumadeMatrices(matrizEstrella, matrizAcumuladora, nodos, nodos);
        for (int i = 0; i < nodos - 1; i++) {
            matrizAcumuladora = productoDeMatrices(matrizAcumuladora, matrizOriginal, nodos, nodos2, nodos, nodos2);
            matrizEstrella = sumadeMatrices(matrizEstrella, matrizAcumuladora, nodos, nodos2);
        }
        return matrizEstrella;
    }

    public static boolean esConexo(int[][] matriz) {
        int[][] matrizEstrella = matrizDeCaminos(matriz);
        for (int i = 0; i < matrizEstrella.length; i++) {
            for (int j = 0; j < matrizEstrella[0].length; j++) {
                if (!(i == j)) {
                    if (matrizEstrella[i][j] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
