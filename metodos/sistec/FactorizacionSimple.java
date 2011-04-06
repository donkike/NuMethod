
package metodos.sistec;


public class FactorizacionSimple {

    private static double[][] L, U;

    public static double[] solucionar(double[][] A, double[] B) {
        int n = A.length;
        U = A;
        L = new double[n][n];
        
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                L[i][k] = U[i][k] / U[k][k];
                for (int j = k; j < n; j++) {
                    U[i][j] -= L[i][k]*U[k][j];
                }
            }
        }
        for (int i = 0; i < n; i++)
            L[i][i] = 1;

        double[] Z = new double[n];
        for (int i = 0; i < n; i++) {
            double acum = 0;
            for (int p = 0; p < i; p++)
                acum += L[i][p]*Z[p];
            Z[i] = B[i] - acum;
        }

        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double acum = 0;
            for (int p = i + 1; p < n; p++)
                acum += U[i][p]*X[p];
            X[i] = (Z[i] - acum) / U[i][i];
        }

        return X;
    }

    public static double[][] getL() {
        return L;
    }

    public static double[][] getU() {
        return U;
    }

}
