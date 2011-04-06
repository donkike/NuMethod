
package metodos.sistec;

import metodos.Util;

public class GaussianaPivTotal {

    public static double[][] P;

    public static double[][][] solucionar(double[][] A, double[] B) {
        int n = A.length;
        double AA[][][] = new double[n-1][n][n+1];
        A = LibreriaBase.extenderMatriz(A, B);
        P = new double[n][n];
        for (int i = 0; i < n; i++)
            P[i][i] = 1;

        for (int k = 0; k < n - 1; k++) {
            double mayor = 0;
            int posFila = -1, posCol = -1;
            for (int i = k; i < n; i++) {
                for (int j = k; j < n; j++) {
                    if (Math.abs(A[i][j]) > mayor) {
                        mayor = Math.abs(A[i][j]);
                        posFila = i;
                        posCol = j;
                    }
                }
            }
            if (mayor == 0) return null;
            if (k != posFila) {
                for (int j = 0; j < n+1; j++) {
                   double temp = A[posFila][j];
                   A[posFila][j] = A[k][j];
                   A[k][j] = temp;
                }
            }
            if (k != posCol) {
                for (int i = 0; i < n; i++) {
                    double temp = A[i][posCol];
                    A[i][posCol] = A[i][k];
                    A[i][k] = temp;
                    temp = P[i][posCol];
                    P[i][posCol] = P[i][k];
                    P[i][k] = temp;
                }
            }
            for (int i = k + 1; i < n; i++) {
                double m = A[i][k] / A[k][k];
                for (int j = k; j < n+1; j++) {
                    A[i][j] = A[i][j] - m * A[k][j];
                }
            }
            LibreriaBase.copiarElementos(A, AA[k]);
        }
        return AA;
    }

    public static double[] obtenerResultado(double[][] A) {
        double[] X = LibreriaBase.sustitucionRegresiva(A);
        X = Util.multiplicar(P, X);
        return X;
    }


}
