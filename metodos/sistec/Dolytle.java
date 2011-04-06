package metodos.sistec;

public class Dolytle {

    private static double[][] L, U;

    public static double[] solucionar(double[][] A, double[] b) {
        if (A.length != A[0].length) {
            return null;
        }
        int n = A.length;

        // Lii = 1
        L = new double[n][n];
        U = new double[n][n];
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
        }

        // Factorización
        for (int k = 0; k < n; k++) {
            for (int j = k; j < n; j++) {
                double acum = 0;
                for (int p = 0; p < k; p++) {
                    acum += (L[k][p] * U[p][j]);
                }
                U[k][j] = A[k][j] - acum;
            }
            for (int i = k + 1; i < n; i++) {
                double acum = 0;
                for (int p = 0; p <= k - 1; p++) {
                    acum += (L[i][p] * U[p][k]);
                }
                L[i][k] = (A[i][k] - acum) / U[k][k];
            }
        }

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
