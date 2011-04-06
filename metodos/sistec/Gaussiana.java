
package metodos.sistec;

public class Gaussiana {

    /*
     * Retorna un arreglo de matrices, donde cada matriz es la resultante
     * de la etapa correspondiente. La Ãºltima posiciÃ³n es la matriz resultante
     * de la eliminaciÃ³n.
     */

    public static double[][][] solucionar(double[][] A, double[] B) {
        int n = A.length;
        double[][][] AA = new double[n - 1][n][n + 1];
        for (int i = 0; i < n; i++) {
            int j = 0;
            for (j = 0; j < n; j++) {
                AA[0][i][j] = A[i][j];
            }
            AA[0][i][j] = B[i];
        }
        A = new double[n][n + 1];
        LibreriaBase.copiarElementos(AA[0], A);
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                double m = A[i][k] / A[k][k];
                for (int j = k; j < n + 1; j++) {
                    A[i][j] = A[i][j] - m*A[k][j];
                }
            }
            LibreriaBase.copiarElementos(A, AA[k]);
        }
        return AA;
    }

    /*
     * Realiza la sustituciÃ³n regresiva a la matriz resultante
     * de la eliminaciÃ³n.
     */

    public static double[] obtenerResultado(double[][] A) {
        int n = A.length;
        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double acum = 0;
            for (int p = i + 1; p < n; p++) {
                acum += A[i][p]*X[p];
            }
            X[i] = (A[i][n] - acum) / A[i][i];
        }
        return X;
    }

}