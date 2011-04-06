
package metodos.sistec;

public class GaussianaPivParcial {

    public static double[][][] solucionar(double[][] A, double[] b) {
        int n = A.length;
        double[][][] AA = new double[n-1][n][n+1];
        A = LibreriaBase.extenderMatriz(A, b);
        for (int k = 0; k < n - 1; k++) {
            //Buscar el mayor en la columna
            double mayor = 0;
            int posMayor = -1;
            for (int i = k; i < n; i++) {
                if (Math.abs(A[i][k]) > mayor) {
                    mayor = Math.abs(A[i][k]);
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
                for (int i = 0; i < n+1; i++) {
                    temp = A[posMayor][i];
                    A[posMayor][i] = A[k][i];
                    A[k][i] = temp;
                }
            }

            double Mik = -1;
            for (int i = k + 1; i < n; i++){
                Mik = A[i][k] / A[k][k];
                for (int j = k; j < n + 1; j++){
                    A[i][j] = A[i][j] - (Mik * A[k][j]);
                } //j
            }//i
            int h = k + 1;
            System.out.println("Etapa " + String.valueOf(h));
            LibreriaBase.copiarElementos(A, AA[k]);
        }// k
        return AA;
    }

    public static double[] obtenerResultado(double[][] A) {
        double[] X = LibreriaBase.sustitucionRegresiva(A);
        return X;
    }

}