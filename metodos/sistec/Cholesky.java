package metodos.sistec;

public class Cholesky {

    private static double[][] L, U;

    public static double[] solucionar(double[][] A, double[] b) {
        int n = A.length;
        L = new double[n][n];
        U = new double[n][n];

        for (int k = 0; k < n; k++) {
            double acum = 0;
            for (int p = 0; p < k - 1; p++) {
                acum += L[k][p] * U[p][k];
            }
            U[k][k] = Math.sqrt(A[k][k] - acum);
            L[k][k] = U[k][k];
            for (int i = k + 1; i < n; i++) {
                acum = 0;
                for (int p = 0; p < k; p++) {
                    acum += L[i][p] * U[p][k];
                }
                L[i][k] = (A[i][k] - acum) / L[k][k];
            }
            for (int j = k + 1; j < n; j++) {
                acum = 0;
                for (int p = 0; p < k; p++) {
                    acum += L[k][p] * U[p][j];
                }
                U[k][j] = (A[k][j] - acum) / U[k][k];
            }
        }

        LibreriaBase.imprimirMatriz(L);

        double[] z = LibreriaBase.sustitucionProgresiva(L, b);
        double[] x = LibreriaBase.sustitucionRegresiva(U, z);

        return x;
    }

    public static double[][] getL() {
        return L;
    }

    public static double[][] getU() {
        return U;
    }

    
}
