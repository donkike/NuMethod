/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.sistec;

/**
 *
 * @author The NuMethod Team
 */
public class GaussianaPivoteoParcial {

    public static double[] solucionar(double[][] A, double[] b) {
        double[] X;
        A = LibreriaBase.extenderMatriz(A, b);
        int n = A.length - 1;
        X = new double[n];
        for (int k = 0; k < n - 1; k++) {
            //Buscar el mayor en la columna
            double mayor = 0;
            int posMayor = -1;
            for (int i = k; i < A[0].length - 1; i++) {
                if (A[i][k] > mayor) {
                    mayor = A[i][k];
                    posMayor = i;
                }
            }
            //salida con error
            if (mayor == 0) {
                return null;
            }

            if (k != posMayor) {
                //el mayor esta en otra fila, cambiar
                double temp;
                for (int i = 0; i < A[0].length - 1; i++) {
                    temp = A[posMayor][i];
                    A[posMayor][i] = A[k][i];
                    A[k][i] = temp;
                }
            }

            double Mik = -1;
            for (int i = k + 1; i <= n; i++){
                Mik = A[i][k] / A[k][k];
                for (int j = k; j <= n + 1; j++){
                    A[i][j] = A[i][j] - (Mik * A[k][j]);
                } //fin ciclo j
            }//fin Ciclo i


        }//fin Ciclo k
        X = LibreriaBase.sustitucionRegresiva(A);
        return X;
    }

}
