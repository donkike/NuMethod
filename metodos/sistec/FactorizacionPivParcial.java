package metodos.sistec;

import metodos.Util;

public class FactorizacionPivParcial {

    private static double[][] L, U;

    public static double[] solucionar(double[][] A, double[] B) {
        int n = A.length;
        U = A;
        L = new double[n][n];
        double[][] P = new double[n][n];

        for (int i = 0; i < n; i++) {
            P[i][i] = 1;
        }

        // Encontrar mayot en columna actual
        for (int k = 0; k < n - 1; k++) {
            double mayor = 0;
            int pos_mayor = -1;
            for (int i = k; i < n; i++) {
                if (U[i][k] > mayor) {
                    mayor = U[i][k];
                    pos_mayor = i;
                }
            }
            if (mayor == 0) // Si mayor == 0, no hay solución
            {
                return null;
            }

            // Hacer intercambio de filas de ser necesario
            if (pos_mayor != k) {
                for (int i = 0; i < n; i++) {
                    double temp = U[k][i];
                    U[k][i] = U[pos_mayor][i];
                    U[pos_mayor][i] = temp;
                    temp = L[k][i];
                    L[k][i] = L[pos_mayor][i];
                    L[pos_mayor][i] = temp;
                    temp = P[k][i];
                    P[k][i] = P[pos_mayor][i];
                    P[pos_mayor][i] = temp;
                }
            }
            for (int i = k + 1; i < n; i++) {
                L[i][k] = U[i][k] / U[k][k];
                for (int j = k; j < n; j++) {
                    U[i][j] -= L[i][k] * U[k][j];
                }
            }
        }

        // Lii = 1
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
        }

        B = Util.multiplicar(P, B); //

        // Sustitución progresiva
        double[] Z = new double[n];
        for (int i = 0; i < n; i++) {
            double acum = 0;
            for (int p = 0; p < i; p++) {
                acum += L[i][p] * Z[p];
            }
            Z[i] = B[i] - acum;
        }

        // Sustitución regresiva
        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double acum = 0;
            for (int p = i + 1; p < n; p++) {
                acum += U[i][p] * X[p];
            }
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
