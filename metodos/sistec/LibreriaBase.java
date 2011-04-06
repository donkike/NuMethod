/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metodos.sistec;

/**
 * Esta Clase contiene los metodos basicos para solucionar los sistemas de ecuaciones
 * <p>
 * Cada metodo de esta clase es declarado como estatico para facilitar su uso por otras clases
 * <p>
 * La version Actual (2.0) ha sido mejorada para utilizar mas metodos, y perfeccionar algunos metodos que estaban funcionando mal
 * @author The NuMethod Team
 * @version 2.0 
 */
public class LibreriaBase {

    public static void copiarElementos(double[][] A, double[][] B) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++)
                B[i][j] = A[i][j];
        }
    }

    public static double[] sustitucionProgresiva(double[][] L, double[] b) {
        int n = L.length;
        double[] z = new double[n];
        for (int j = 0; j < n; j++) {
                double acum = 0;
                for (int p = 0; p <= j - 1; p++)
                    acum += (L[j][p] * z[p]);
                z[j] = (b[j] - acum) / L[j][j];
        }
        return z;
    }

    public static double[] sustitucionRegresiva(double[][] U, double[] z) {
        int n = U.length;
        double[] x = new double[n];
        for (int j = n - 1; j >= 0; j--) {
                double acum = 0;
                for (int p = j + 1; p < n; p++)
                    acum += (U[j][p] * x[p]);
                x[j] = (z[j] - acum) / U[j][j];
        }
        return x;
    }

    public static double[] sustitucionRegresiva (double[][] A){
        int n = A.length;
        double[] X = new double[n];
        double acum;
        for (int i = n - 1; i >= 0; i--) {
            acum = 0;
            for (int p = i + 1; p < n; p++) {
                acum += A[i][p]*X[p];
            }
            X[i] = (A[i][n] - acum) / A[i][i];
        }
        return X;
    }

    public static double normaEuclidiana(double[] V) {
        double acum = 0;
        for (int i = 0; i < V.length; i++)
            acum += (V[i]*V[i]);
        return Math.sqrt(acum);
    }

    public static double norma(double[] V) {
        double mayor = 0;
        for (int i = 0; i < V.length; i++) {
            if (mayor < Math.abs(V[i]))
                mayor = Math.abs(V[i]);
        }
        return mayor;
    }

    public static double[] restarVectores(double[] V1, double[] V2) {
        if (V1.length != V2.length) return null;
        double[] Vr = new double[V1.length];
        for (int i = 0; i < V1.length; i++)
            Vr[i] = V1[i] - V2[i];
        return Vr;
    }

    public static double[][] eliminacionGaussiana (double[][]A){
        double Mik = 0;
        int n = A.length;
        for (int k = 0; k<n - 1; k++){
            for (int i = k + 1; i < n; i++){
                Mik = A[i][k] / A[k][k];
                for (int j = k; j < n + 1; j++){
                    A[i][j] = A[i][j] - Mik * A[k][j];
                }
            }
        }

        return A;
    }

    public static double[][] extenderMatriz (double[][]A, double []B){
        int n = A.length;
        double[][] AA = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            int j = 0;
            for (j = 0; j < n; j++) {
                AA[i][j] = A[i][j];
            }
            AA[i][j] = B[i];
        }

        return AA;
    }

    public static double[][] Metodo_LU (double[][]A, double[][]U){
        int n = A.length;
        double[][]L = new double[n][n];
        //Inicializacion y llenado de la matriz L
        for (int i = 0; i < n; i++){
            for (int j = 0; i < n; j++){
                if (i == j){
                    L[i][j] = 1;
                }
                else
                    if (i != j){
                        L[i][j] = 0;
                    }
            }
        }
        return L;
    }

    public static void imprimirMatriz(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++)
                System.out.print(A[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void imprimirVector(double[] V) {
        for (int i = 0; i < V.length; i++)
            System.out.print(V[i] + " ");
        System.out.println("\n");
    }

}