

package metodos.sistec;

import metodos.Util;

public class MatrizInversa {

    /*
     * Halla la inversa de una matriz utilizando el método de Dolytle
     */
    public static double[][] solucionar(double[][] A) {
        if (A.length != A[0].length)
            return null;
        int n = A.length;

        // Lii = 1
        double[][] L = new double[n][n], U = new double[n][n];
        for (int i = 0; i < n; i++)
            L[i][i] = 1;

        // Factorización
        for (int k = 0; k < n; k++) {
            for (int j = k; j < n; j++) {
                double acum = 0;
                for (int p = 0; p <= k - 1; p++)
                    acum += (L[k][p] * U[p][j]);
                U[k][j] = A[k][j] - acum;
            }
            for (int i = k + 1; i < n; i++) {
                double acum = 0;
                for (int p = 0; p <= k - 1; p++)
                    acum += (L[i][p] * U[p][k]);
                L[i][k] = (A[i][k] - acum) / U[k][k];
            }
        }

        // Sustitución
        double[][] Ainv = new double[n][n];
        for (int i = 0; i < n; i++) {
            double[] b = new double[n], z = new double[n], x = new double[n];
            b[i] = 1;

            // Sustitución progresiva
            for (int j = 0; j < n; j++) {
                double acum = 0;
                for (int p = 0; p <= j - 1; p++)
                    acum += (L[j][p] * z[p]);
                z[j] = (b[j] - acum);
            }

            // Sustitución regresiva
            for (int j = n - 1; j >= 0; j--) {
                double acum = 0;
                for (int p = j + 1; p < n; p++)
                    acum += (U[j][p] * x[p]);
                x[j] = (z[j] - acum) / U[j][j];
            }

            // Asignación de valores a columna de inversa de A
            for (int j = 0; j < n; j++)
                Ainv[j][i] = x[j];
        }
        return Ainv;
    }

// <editor-fold defaultstate="collapsed" desc="Ejecutor de pruebas">
//    public static void main(String[] args) {
//        double [][] L = {
//            {2,0,0},
//            {4,7,0},
//            {-4,5,-4}
//        };
//
//        double [][] U = {
//            {1,9,-6},
//            {0,2,7},
//            {0,0,3}
//        };
//
//        double[][]A = Util.multiplicar(L, U);
//        LibreriaBase.imprimirMatriz(A);
//
//        double[][]Ainv = MatrizInversa.solucionar(A);
//
//        System.out.println("Resultado");
//        LibreriaBase.imprimirMatriz(Ainv);
//    }// </editor-fold>
}
